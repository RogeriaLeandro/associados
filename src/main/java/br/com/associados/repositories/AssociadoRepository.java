package br.com.associados.repositories;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import br.com.associados.model.Associado;

public interface AssociadoRepository extends PagingAndSortingRepository<Associado, String> {
    Optional<Associado> findByDocumento(String documento);
}
