package br.com.associados.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.associados.model.Associado;
import br.com.associados.model.TipoPessoa;
import br.com.associados.repositories.AssociadoRepository;

import java.util.UUID;

@Configuration
public class PopuladorH2 implements CommandLineRunner{
    
    @Autowired
    private AssociadoRepository associadoRepository;

    @Override
    public void run(String... args) throws Exception {
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "26133117036", TipoPessoa.PF, "João"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "45522000000190", TipoPessoa.PJ, "Padaria do pão"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "94738515020", TipoPessoa.PF, "Maria"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "56207226000112", TipoPessoa.PJ, "Mercado 123"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "61428051015", TipoPessoa.PF, "José"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "68218759000189", TipoPessoa.PJ, "Internet do bairro"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "02733709011", TipoPessoa.PF, "Marcos"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "00037470000120", TipoPessoa.PJ, "Banco 321"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "60099040050", TipoPessoa.PF, "Adalberto"));
        associadoRepository.save(new Associado(UUID.randomUUID().toString(), "62631979000153", TipoPessoa.PJ, "Academia aaa"));

    }

//    @Override
//    public void run(String... args) throws Exception {
//        associadoRepository.save(new Associado("5c7f6cda-6d20-4f9d-b00c-01ccb3d17a45", "26133117036", TipoPessoa.PF, "João"));
//        associadoRepository.save(new Associado("2dd491df-997c-4111-b806-fe5553b4a892", "45522000000190", TipoPessoa.PJ, "Padaria do pão"));
//        associadoRepository.save(new Associado("32ce9e27-926a-458a-9a1e-c822d164c167", "94738515020", TipoPessoa.PF, "Maria"));
//        associadoRepository.save(new Associado("492886f3-fbbd-4a25-9ebe-6308b8e072bd", "56207226000112", TipoPessoa.PJ, "Mercado 123"));
//        associadoRepository.save(new Associado("16544f34-cd4e-412f-ba17-14d05cbe219f", "61428051015", TipoPessoa.PF, "José"));
//        associadoRepository.save(new Associado("8e09e97d-4279-48ab-9bf2-9d503fa6976a", "68218759000189", TipoPessoa.PJ, "Internet do bairro"));
//        associadoRepository.save(new Associado("89f51b89-5971-4672-a6c7-529b993eccd7", "02733709011", TipoPessoa.PF, "Marcos"));
//        associadoRepository.save(new Associado("a5d52e43-dd0b-4aea-bbc5-97ddd077f9ee", "00037470000120", TipoPessoa.PJ, "Banco 321"));
//        associadoRepository.save(new Associado("e0a20b84-3a4e-48c5-bdb0-42f05a66ce20", "60099040050", TipoPessoa.PF, "Adalberto"));
//        associadoRepository.save(new Associado("0b9452c6-1fa8-4ce5-851f-fc656ef9d2f6", "62631979000153", TipoPessoa.PJ, "Academia aaa"));
//    }
}


