package br.com.associados.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.associados.model.Associado;


public interface AssociadoRepository extends JpaRepository<Associado, UUID>{
    
	// public Page<AssociadoDTO> findAll(Pageable pageable);

	@Query(value = "SELECT a.* from associado a WHERE a.documento = :documento", nativeQuery = true)
	public Associado buscaAssociadoPorDocumento(String documento);
}
