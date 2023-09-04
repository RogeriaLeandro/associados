package br.com.associados.v1.controller;

import br.com.associados.integracao.boleto.service.BoletoService;
import br.com.associados.model.TipoPessoa;
import br.com.associados.repositories.AssociadoRepository;
import br.com.associados.v1.dto.AssociadoRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@TestPropertySource("/test.properties")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssociadoControllerSucessoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AssociadoRepository associadoRepository;

    @SpyBean
    private BoletoService boletoService;

    private static final String ENDPOINT_V1_ASSOCIADOS = "http://127.0.0.1:8090/v1/associado";
    private static final String ENDPOINT_V1_ASSOCIADOS_ID = "http://127.0.0.1:8090/v1/associado/{id}";
    private static final String ENDPOINT_V1_CONSULTAR_ASSOCIADO_POR_DOCUMENTO = "http://127.0.0.1:8090/v1/associado/documento";

    @Test
    @SneakyThrows
    void sucessoAoCadastrarAssociado() {
        var associadoRequestDTO = AssociadoRequestDTO.builder()
                .nome("joao")
                .documento("12312312312")
                .tipoPessoa(TipoPessoa.PF)
                .build();

        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var json = ow.writeValueAsString(associadoRequestDTO);

        mockMvc.perform(post(ENDPOINT_V1_ASSOCIADOS)
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void sucessoAoAlterarAssociado() {
        var associado = associadoRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
        var associadoRequestDTO = AssociadoRequestDTO.builder()
                .nome("Nome alterado")
                .build();

        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var json = ow.writeValueAsString(associadoRequestDTO);

        mockMvc.perform(put(ENDPOINT_V1_ASSOCIADOS_ID, associado.getUuid())
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void sucessoAoConsultarAssociadoPordocumento() {
        var associado = associadoRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
        mockMvc.perform(get(ENDPOINT_V1_CONSULTAR_ASSOCIADO_POR_DOCUMENTO)
                        .queryParam("documento", associado.getDocumento()))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void sucessoAoConsultarAssociadoPorId() {
        var associado = associadoRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
        mockMvc.perform(get(ENDPOINT_V1_ASSOCIADOS_ID, associado.getUuid()))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void sucessoAoDeletarAssociado() {
        var associado = associadoRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
        //TODO: REMOVER MOCK APOS IMPLEMENTAR API DE BOLETOS
        doReturn(false).when(boletoService).possuiBoletoAPagar(associado.getUuid().toString());
        mockMvc.perform(delete(ENDPOINT_V1_ASSOCIADOS_ID, associado.getUuid()))
                .andExpect(status().isNoContent());
    }

}
