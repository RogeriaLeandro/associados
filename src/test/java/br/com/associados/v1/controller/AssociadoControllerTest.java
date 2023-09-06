package br.com.associados.v1.controller;

import br.com.associados.AssociadoTestHelper;
import br.com.associados.v1.services.AssociadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static br.com.associados.AssociadoTestHelper.*;
import static br.com.associados.model.TipoPessoa.PF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociadoControllerTest {

    @Mock
    private AssociadoService associadoService;

    @InjectMocks
    private AssociadoController target;

    @Test
    void consultarAssociados(){
        var associados = AssociadoTestHelper.criarAssociadosDTO();
        doReturn(associados).when(associadoService).consultarAssociados(1);
        var response = target.consultarAssociados(1);
        assertEquals(associados, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(associadoService).consultarAssociados(1);
    }

    @Test
    void consultarAssociado(){
        var associado = AssociadoTestHelper.criarAssociadosDTO().get(1);
        associado.setId(ID_ASSOCIADO);
        var optionalAssociado = Optional.of(associado);
        doReturn(optionalAssociado).when(associadoService).consultarAssociado(ID_ASSOCIADO);
        var response = target.consultarAssociado(ID_ASSOCIADO);
        assertEquals(associado, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(associadoService).consultarAssociado(ID_ASSOCIADO);
    }

    @Test
    void nenhumAssociadoEncontradoAoConsultarAssociado(){
        doReturn(Optional.empty()).when(associadoService).consultarAssociado(ID_ASSOCIADO);
        var response = target.consultarAssociado(ID_ASSOCIADO);
        assertEquals(null, response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(associadoService).consultarAssociado(ID_ASSOCIADO);
    }

    @Test
    void consultarAssociadoPorDocumento(){
        var associado = AssociadoTestHelper.criarAssociadosDTO().get(1);
        associado.setDocumento(CPF);
        var optionalAssociado = Optional.of(associado);
        doReturn(optionalAssociado).when(associadoService).consultarAssociadoPorDocumento(CPF);
        var response = target.consultarAssociadoPorDocumento(CPF);
        assertEquals(associado, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(associadoService).consultarAssociadoPorDocumento(CPF);
    }

    @Test
    void nenhumAssociadoEncontradoAoConsultarAssociadoPorDocumento(){
        doReturn(Optional.empty()).when(associadoService).consultarAssociadoPorDocumento(CPF);
        var response = target.consultarAssociadoPorDocumento(CPF);
        assertEquals(null, response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(associadoService).consultarAssociadoPorDocumento(CPF);
    }

    @Test
    void cadastrarAssociado(){
        var associadoRequest = criarAssociadoRequest(PF);
        var associadoDTO = criarAssociadoDTO(associadoRequest);
        doReturn(associadoDTO).when(associadoService).cadastrarAssociado(associadoRequest);
        var response = target.cadastrarAssociado(associadoRequest);
        assertEquals(associadoDTO, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(associadoService).cadastrarAssociado(associadoRequest);
    }

    @Test
    void alterarAssociado(){
        var associadoRequest = criarAssociadoRequest(PF);
        var associadoDTO = criarAssociadoDTO(associadoRequest);
        associadoDTO.setNome("Nome alterado");
        doReturn(associadoDTO).when(associadoService).alterarAssociado(associadoDTO.getId(), associadoRequest);
        var response = target.alterarAssociado(associadoDTO.getId(), associadoRequest);
        assertEquals(associadoDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(associadoService).alterarAssociado(associadoDTO.getId(), associadoRequest);
    }

    @Test
    void deletarAssociado(){
        var response = target.deletarAssociado(ID_ASSOCIADO);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(associadoService).deletarAssociado(ID_ASSOCIADO);
    }


    @Test
    void validaDocumento() {
        var response = target.validarDocumento(CPF);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(associadoService).validaDocumento(CPF);
    }

}