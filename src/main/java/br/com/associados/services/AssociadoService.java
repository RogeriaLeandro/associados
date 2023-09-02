package br.com.associados.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.associados.exceptions.DocumentoInvalidoException;
import br.com.associados.exceptions.EntidadeNaoEncontradaException;
import br.com.associados.model.Associado;
import br.com.associados.model.AssociadoDTO;
import br.com.associados.repositories.AssociadoRepository;
import br.com.associados.utils.AssociadoUtil;

@Service
public class AssociadoService {
    	
    @Autowired
	private AssociadoRepository associadoRepository;

    @Autowired
    private AssociadoUtil associadoUtil;

    private static Logger logger = LoggerFactory.getLogger(AssociadoService.class);

	// public Page<AssociadoDTO> findAll(Pageable pageable){
	   
    // //    List<AssociadoDTO> listaAssociados = associadoRepository.findAll(pageable);
        
        
       
     
    //     return listaAssociados;
	// }
	


















	public Associado findById(UUID uuid) {
		Optional<Associado> associado = associadoRepository.findById(uuid);
		return associado.get();
		
	}

    public Associado findByDocumento(String documento) {
		Associado associado = associadoRepository.buscaAssociadoPorDocumento(documento);
		return associado;
		
	}

    /**
     * [cadastrarAssociado description]
     *
     * @return  boolean [return description]
     * método cadastra tanto um novo associado quanto altera um associado
     */
	public boolean cadastrarAssociado(Associado associado) {
		
        boolean documentoValido = associadoUtil.validaDocumento(associado);
		if (documentoValido) {
            try {
                associadoRepository.save(associado);
                return true;
            } catch (DocumentoInvalidoException e){
                throw new DocumentoInvalidoException ("Associado não pode ser cadastrado pois o documento não é válido.");
            }
        }

        return documentoValido;

	}

    public void deleteAssociado(UUID uuid) {

        try {

            //TODO Não deve permitir excluir um associado que possua boletos que ainda não foram pagos.
            //TODO Fazer essa condição e lançar exceção caso haja boleto
			associadoRepository.deleteById(uuid);
		} catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe Associado para o código %d", uuid));
		}

    }

}
