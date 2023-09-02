package br.com.associados.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.associados.model.Associado;
import br.com.associados.repositories.AssociadoRepository;

@Configuration
public class PopuladorH2 implements CommandLineRunner{
    @Autowired
    private AssociadoRepository associadoRepository;

    @Override
    public void run(String... args) throws Exception {
        Associado associado1 = new Associado(null, "36104399126", "PF", "Associado 1");
        Associado associado2 = new Associado(null, "71871128000179", "PJ", "Associado 2");

        associadoRepository.save(associado1);
        associadoRepository.save(associado2);
    }
}


