package br.com.associados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.associados.model.Associado;
import br.com.associados.repositories.AssociadoRepository;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableFeignClients
public class AssociadosApplication{

	public static void main(String[] args) {
		SpringApplication.run(AssociadosApplication.class, args);
	}
}
