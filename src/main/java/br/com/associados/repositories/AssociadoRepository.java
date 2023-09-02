package br.com.associados.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.associados.model.Associado;
import br.com.associados.model.AssociadoDTO;

public interface AssociadoRepository extends PagingAndSortingRepository<AssociadoDTO, UUID>{
    
	public Page<AssociadoDTO> findAll(Pageable pageable);

	@Query(value = "SELECT a.* from associado a WHERE a.documento = :documento", nativeQuery = true)
	public Associado buscaAssociadoPorDocumento(String documento);
}
