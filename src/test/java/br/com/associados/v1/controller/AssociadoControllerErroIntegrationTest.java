package br.com.associados.v1.controller;

import br.com.associados.integracao.boleto.service.BoletoService;
import br.com.associados.model.TipoPessoa;
import br.com.associados.repositories.AssociadoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.associados.v1.dto.AssociadoRequestDTO;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AssociadoControllerErroIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AssociadoRepository associadoRepository;

    @SpyBean
    private BoletoService boletoService;

//    @Value("${associado-api.url}")
//    private String associadoApiUrl;

    private String ENDPOINT_V1_ASSOCIADOS = "/v1/associados";
    private String ENDPOINT_V1_ASSOCIADOS_ID = "/v1/associados/{id}";
    private String ENDPOINT_V1_CONSULTAR_ASSOCIADO_POR_DOCUMENTO = "/v1/associados/documento";

    @ParameterizedTest
    @ValueSource(strings = {"1", "  ", "abcd"})
    @SneakyThrows
    void identificadorAssociadoInvalidoAoConsultarAssociado(String idAssociado) {
        mockMvc.perform(get(ENDPOINT_V1_ASSOCIADOS_ID, idAssociado))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("consultarAssociado.id: O ID deve ter formato de UUID"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "  ", "abcd"})
    @SneakyThrows
    void documentoAssociadoInvalidoAoConsultarAssociadoPorDocumento(String documento) {
        mockMvc.perform(get(ENDPOINT_V1_CONSULTAR_ASSOCIADO_POR_DOCUMENTO)
                        .queryParam("documento", documento))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("consultarAssociadoPorDocumento.documento: O documento deve ser um CPF ou CNPJ válido"));
    }

    @Test
    @SneakyThrows
    void falhaDocumentoInvalidoAoCadastrarAssociado() {
        var associadoRequestDTO = AssociadoRequestDTO.builder()
                .nome("joao")
                .documento("1")
                .tipoPessoa(TipoPessoa.PF)
                .build();

        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var json = ow.writeValueAsString(associadoRequestDTO);

        mockMvc.perform(post(ENDPOINT_V1_ASSOCIADOS)
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void falhaNomeNaoInformadoAoCadastrarAssociado() {
        var associadoRequestDTO = AssociadoRequestDTO.builder()
                .documento("12312312387")
                .tipoPessoa(TipoPessoa.PF)
                .build();

        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var json = ow.writeValueAsString(associadoRequestDTO);

        mockMvc.perform(post(ENDPOINT_V1_ASSOCIADOS)
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void falhaTipoPessoaInconsistenteAoCadastrarAssociado() {
        var associadoRequestDTO = AssociadoRequestDTO.builder()
                .nome("joao")
                .documento("076174367-77")
                .tipoPessoa(TipoPessoa.PJ)
                .build();

        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var json = ow.writeValueAsString(associadoRequestDTO);

        mockMvc.perform(post(ENDPOINT_V1_ASSOCIADOS)
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Tipo de documento não bate com o tipo pessoa informado"));
    }

    @Test
    @SneakyThrows
    void falhaTipoPessoaInconsistenteAoAlterarAssociado() {
        var associado = associadoRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
        var associadoRequestDTO = AssociadoRequestDTO.builder()
                .nome("joao")
                .documento("12312312312")
                .tipoPessoa(TipoPessoa.PJ)
                .build();

        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var json = ow.writeValueAsString(associadoRequestDTO);

        mockMvc.perform(put(ENDPOINT_V1_ASSOCIADOS_ID, associado.getId())
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Tipo de documento não bate com o tipo pessoa informado"));
    }

    @Test
    @SneakyThrows
    void falhaAssociadoNaoEncontradoAoAlterarAssociado() {
        var associadoRequestDTO = AssociadoRequestDTO.builder()
                .nome("joao")
                .documento("12312312312")
                .tipoPessoa(TipoPessoa.PJ)
                .build();

        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var json = ow.writeValueAsString(associadoRequestDTO);
        var id = UUID.randomUUID().toString();
        mockMvc.perform(put(ENDPOINT_V1_ASSOCIADOS_ID, id)
                        .content(json)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Não foi possível encontrar um associado para o ID: " + id));
    }

    @Test
    @SneakyThrows
    void falhaAssociadoNaoEncontradoAoDeletarAssociado() {
        var id = "041dc250-724e-45bd-97c5-e3fd1021beba";
        mockMvc.perform(delete(ENDPOINT_V1_ASSOCIADOS_ID, id)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Não foi possível encontrar um associado para o ID: " + id));
    }

    @Test
    @SneakyThrows
    void falhaAssociadoComBoletoEmAbertoAoDeletarAssociado() {
        var associado = associadoRepository.findAll(PageRequest.of(0, 1)).getContent().get(0);
        //TODO: desligar mock quando subir api de boletos
        doReturn(true).when(boletoService).possuiBoletoAPagar(associado.getId().toString());
        mockMvc.perform(delete(ENDPOINT_V1_ASSOCIADOS_ID, associado.getId())
                        .header("Content-Type", "application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Não é possível deletar um associado com boleto em aberto"));
    }

}
