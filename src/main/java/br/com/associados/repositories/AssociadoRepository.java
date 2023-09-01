package br.com.associados.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.associados.model.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, UUID>{
    
}
