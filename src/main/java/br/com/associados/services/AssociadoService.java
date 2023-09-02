package br.com.associados.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.associados.exceptions.AssociadoNaoEncontradoException;
import br.com.associados.exceptions.FormatoDocumentoInvalidoException;
import br.com.associados.exceptions.TipoPessoaInconsistenteException;
import br.com.associados.model.Associado;
import br.com.associados.model.TipoPessoa;
import br.com.associados.repositories.AssociadoRepository;
import br.com.associados.utils.FormatadorUtil;
import br.com.associados.utils.RegexUtil;
import br.com.associados.v1.dto.AssociadoDTO;
import br.com.associados.v1.dto.AssociadoRequestDTO;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AssociadoService {
    	
    @Value("${app.config.qtd-registros-pagina}")
    private int qtdRegistrosPorPagina = 5;

    private static final int TAMANHO_CPF = 11;

    @Autowired
	private AssociadoRepository associadoRepository;

    public List<AssociadoDTO> consultaAssociados(int pagina) {
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
        var associado = associadoRepository.save(toEntity(associadoRequestDTO));
        return toDTO(associado);
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

        private AssociadoDTO toDTO(Associado associado) {
        return AssociadoDTO.builder()
                .id(associado.getUuid().toString())
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
        associado.setUuid(UUID.randomUUID());
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
