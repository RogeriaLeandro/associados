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
        associadoRepository.save(new Associado("4fd251b7-0358-433f-b5b5-135b37538c1d", "26133117036", TipoPessoa.PF, "João"));
        associadoRepository.save(new Associado("041dc250-724e-45bd-97c5-e3fd1021bebb", "45522000000190", TipoPessoa.PJ, "Padaria do pão"));
        associadoRepository.save(new Associado("c880dbe7-bc59-460d-9965-c41534b18f3b", "94738515020", TipoPessoa.PF, "Maria"));
        associadoRepository.save(new Associado("313b11d6-61a3-46a3-8d47-6840bdc80132", "56207226000112", TipoPessoa.PJ, "Mercado 123"));
        associadoRepository.save(new Associado("296fc370-f5be-4024-b017-9d2d89eb2639", "61428051015", TipoPessoa.PF, "José"));
        associadoRepository.save(new Associado("3dc5ce0b-bc05-4112-82a5-e8caa95243eb", "68218759000189", TipoPessoa.PJ, "Internet do bairro"));
        associadoRepository.save(new Associado("ad89ba0d-3193-4daa-875f-581657f3d875", "02733709011", TipoPessoa.PF, "Marcos"));
        associadoRepository.save(new Associado("e7837eb9-07fe-46eb-b3e0-1dab027aab85", "00037470000120", TipoPessoa.PJ, "Banco 321"));
        associadoRepository.save(new Associado("46be9eee-519e-4849-9c51-afce3cf642ad", "60099040050", TipoPessoa.PF, "Adalberto"));
        associadoRepository.save(new Associado("d60c2fe8-1272-4bae-9334-2012976ebc58", "62631979000153", TipoPessoa.PJ, "Academia aaa"));

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


