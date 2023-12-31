package br.com.associados.v1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.associados.exceptions.*;
import br.com.associados.v1.controller.AssociadoController;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.associados.integracao.boleto.service.BoletoService;
import br.com.associados.model.Associado;
import br.com.associados.model.TipoPessoa;
import br.com.associados.repositories.AssociadoRepository;
import br.com.associados.utils.FormatadorUtil;
import br.com.associados.utils.RegexUtil;
import br.com.associados.v1.dto.AssociadoDTO;
import br.com.associados.v1.dto.AssociadoRequestDTO;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.Document;

@Service
@Slf4j
public class AssociadoService {
    	
    @Value("${app.config.qtd-registros-pagina}")
    private int qtdRegistrosPorPagina = 5;

    private static final int TAMANHO_CPF = 11;

    @Autowired
	private AssociadoRepository associadoRepository;

    @Autowired
    private BoletoService boletoService;

    private static Logger logger = LoggerFactory.getLogger(AssociadoService.class);

    public List<AssociadoDTO> consultarAssociados(int pagina) {
        return associadoRepository.findAll(PageRequest.of(pagina - 1, qtdRegistrosPorPagina))
                .getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<AssociadoDTO> consultarAssociado(String id) {
        return associadoRepository.findById(id).map(this::toDTO);
    }

   public Optional<AssociadoDTO> consultarAssociadoPorDocumento(String documento) {
        return associadoRepository.findByDocumento(formataDocumentoAssociado(documento)).map(this::toDTO);
    }

    private static String formataDocumentoAssociado(String documento) {
        return documento.replaceAll(RegexUtil.REGEX_LIMPA_DOCUMENTO, "");
    }

    public AssociadoDTO cadastrarAssociado(AssociadoRequestDTO associadoRequestDTO) {
        boolean docEValido = validaDocumento(associadoRequestDTO.getDocumento());
        if (docEValido){
            var associado = associadoRepository.save(toEntity(associadoRequestDTO));
            return toDTO(associado);
        } else {
            throw new DocumentoInvalidoException("Este documento é inválido conforme regra nacional.");
        }
    }

    public boolean validaDocumento(String documento) {

        documento = documento.replace("-" , "");
        documento = documento.replace("." , "");

        if (documento.length() == 11) {
            CPFValidator cpfValidator = new CPFValidator();
            try {
                cpfValidator.assertValid(documento);
                return true;
            } catch (Exception e) {
                logger.error("CPF Inválido");
                return false;
            }
        } else {
            CNPJValidator cnpjValidator = new CNPJValidator();
            try {
                cnpjValidator.assertValid(documento);
                return true;
            } catch (Exception e) {
                logger.error("CNPJ Inválido");
                return false;
            }
        }
    }
	

    public AssociadoDTO alterarAssociado(String id, AssociadoRequestDTO associadoRequestDTO) {
        var associado = getAssociado(id);

        if (Objects.nonNull(associadoRequestDTO.getNome())) {
            associado.setNome(associadoRequestDTO.getNome());
        }

        if (Objects.nonNull(associadoRequestDTO.getDocumento())) {

            if (!associadoRequestDTO.getDocumento().matches(RegexUtil.REGEX_DOCUMENTO)) {
                throw new FormatoDocumentoInvalidoException("O documento informado não é válido");
            }

            var documentoFormatado = formataDocumentoAssociado(associadoRequestDTO.getDocumento());
            associado.setDocumento(documentoFormatado.trim());
            if (Objects.nonNull(associadoRequestDTO.getTipoPessoa())) {
                associado.setTipoPessoa(getTipoPessoa(associadoRequestDTO.getTipoPessoa(), documentoFormatado));
            }
        }

        return toDTO(associadoRepository.save(associado));
    }

    public void deletarAssociado(String id) {
        var associado = getAssociado(id);

        if (boletoService.possuiBoletoAPagar(associado.getId().toString())) {
            throw new BoletoEmAbertoException("Não é possível deletar um associado com boleto em aberto");
        }

        associadoRepository.delete(associado);
    }

    private AssociadoDTO toDTO(Associado associado) {
        return AssociadoDTO.builder()
                .id(associado.getId().toString())
                .nome(associado.getNome())
                .documento(FormatadorUtil.formatarDocumento(associado.getDocumento()))
                .tipoPessoa(associado.getTipoPessoa())
                .build();
    }
    
    private Associado getAssociado(String id) {
        return associadoRepository.findById(id).orElseThrow(() -> new AssociadoNaoEncontradoException("Não foi possível encontrar um associado para o ID: " + id));
    }

    private Associado toEntity(AssociadoRequestDTO associadoRequestDTO) {
        var documentoFormatado = formataDocumentoAssociado(associadoRequestDTO.getDocumento()).trim();
        var associado = new Associado();
        associado.setId(UUID.randomUUID().toString());
        associado.setDocumento(documentoFormatado);
        associado.setNome(associadoRequestDTO.getNome());
        associado.setTipoPessoa(getTipoPessoa(associadoRequestDTO.getTipoPessoa(), documentoFormatado));
        return associado;
    }

    private TipoPessoa getTipoPessoa(TipoPessoa tipoPessoa, String documentoFormatado) {
        if (Objects.nonNull(tipoPessoa)) {
            if (tipoPessoa.getSizeDoc() != documentoFormatado.length()) {
                throw new TipoPessoaInconsistenteException("Tipo de documento não bate com o tipo pessoa informado");
            }
            return tipoPessoa;
        }
        return documentoFormatado.length() == TAMANHO_CPF ? TipoPessoa.PF : TipoPessoa.PJ;
    }


}
