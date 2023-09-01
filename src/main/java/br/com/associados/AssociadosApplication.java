package br.com.associados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.associados.model.Associado;
import br.com.associados.repositories.AssociadoRepository;

@SpringBootApplication
public class AssociadosApplication{

	public static void main(String[] args) {
		SpringApplication.run(AssociadosApplication.class, args);
	}
}
