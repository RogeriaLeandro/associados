package br.com.associados.v1.service;

import br.com.associados.exceptions.AssociadoNaoEncontradoException;
import br.com.associados.exceptions.BoletoEmAbertoException;
import br.com.associados.exceptions.FormatoDocumentoInvalidoException;
import br.com.associados.exceptions.TipoPessoaInconsistenteException;
import br.com.associados.integracao.boleto.service.BoletoService;
import br.com.associados.model.Associado;
import br.com.associados.repositories.AssociadoRepository;
import br.com.associados.v1.services.AssociadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.associados.AssociadoTestHelper.*;
import static br.com.associados.model.TipoPessoa.PF;
import static br.com.associados.model.TipoPessoa.PJ;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociadoServiceTest {

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private BoletoService boletoService;

    @InjectMocks
    private AssociadoService target;

    @Test
    void consultarAssociados() {
        var associados = criarAssociados();
        Page<Associado> pageAssociados = new PageImpl(associados);
        var associadosDTO = criarAssociadosDTO(associados);
        doReturn(pageAssociados).when(associadoRepository).findAll(PageRequest.of(1 - 1, 5));
        var actual = target.consultarAssociados(1);
        assertEquals(associadosDTO, actual);
        verify(associadoRepository).findAll(PageRequest.of(1 - 1, 5));
        verifyNoInteractions(boletoService);
    }

    @Test
    void nenhumAssociadoEncontradoAoConsultarAssociados() {
        Page<Associado> pageAssociados = new PageImpl(List.of());
        doReturn(pageAssociados).when(associadoRepository).findAll(PageRequest.of(1 - 1, 5));
        var actual = target.consultarAssociados(1);
        assertEquals(List.of(), actual);
        verify(associadoRepository).findAll(PageRequest.of(1 - 1, 5));
        verifyNoInteractions(boletoService);
    }

    @Test
    void consultarAssociado() {
        var associado = criarAssociados().get(0);
        associado.setId(ID_ASSOCIADO);
        var associadoDTO = Optional.of(criarAssociadoDTO(associado));
        doReturn(Optional.of(associado)).when(associadoRepository).findById(ID_ASSOCIADO);
        var actual = target.consultarAssociado(ID_ASSOCIADO);
        assertEquals(associadoDTO, actual);
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verifyNoInteractions(boletoService);
    }

    @Test
    void nenhumAssociadoEncontradoAoConsultarAssociado() {
        doReturn(Optional.empty()).when(associadoRepository).findById(ID_ASSOCIADO);
        var actual = target.consultarAssociado(ID_ASSOCIADO);
        assertEquals(Optional.empty(), actual);
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verifyNoInteractions(boletoService);
    }

    @Test
    void consultarAssociadoPorDocumento() {
        var associado = criarAssociados().get(0);
        associado.setDocumento(CPF);
        var associadoDTO = Optional.of(criarAssociadoDTO(associado));
        doReturn(Optional.of(associado)).when(associadoRepository).findByDocumento(CPF_NAO_FORMATADO);
        var actual = target.consultarAssociadoPorDocumento(CPF);
        assertEquals(associadoDTO, actual);
        verify(associadoRepository).findByDocumento(CPF_NAO_FORMATADO);
        verifyNoInteractions(boletoService);
    }

    @Test
    void nenhumAssociadoEncontradoAoConsultarAssociadoPorDocumento() {
        doReturn(Optional.empty()).when(associadoRepository).findByDocumento(CPF_NAO_FORMATADO);
        var actual = target.consultarAssociadoPorDocumento(CPF);
        assertEquals(Optional.empty(), actual);
        verify(associadoRepository).findByDocumento(CPF_NAO_FORMATADO);
        verifyNoInteractions(boletoService);
    }

    @Test
    void cadastrarAssociado() {
        var associadoRequest = criarAssociadoRequest(PF);
        var associado = criarAssociado(associadoRequest);
        var associadoDTO = criarAssociadoDTO(associadoRequest);
        associadoDTO.setDocumento(CPF);
        associadoRequest.setDocumento(CPF_NAO_FORMATADO);
        associado.setDocumento(CPF_NAO_FORMATADO);
        doReturn(associado).when(associadoRepository).save(refEq(associado, "id"));
        boolean eValido = target.validaDocumento(associadoDTO.getDocumento());
        var actual = target.cadastrarAssociado(associadoRequest);
        associadoDTO.setId(associado.getId().toString());
        assertEquals(associadoDTO, actual);
        assertTrue(eValido);
        verify(associadoRepository).save(refEq(associado, "id"));
        verifyNoInteractions(boletoService);
    }

    @Test
    void erroAssociadoNaoEncontradoAoAlterarAssociado() {
        var associadoRequest = criarAssociadoRequest(PJ);
        doReturn(Optional.empty()).when(associadoRepository).findById(ID_ASSOCIADO);
        var exception = assertThrows(AssociadoNaoEncontradoException.class, () -> target.alterarAssociado(ID_ASSOCIADO, associadoRequest));
        assertEquals("Não foi possível encontrar um associado para o ID: " + ID_ASSOCIADO, exception.getMessage());
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verifyNoInteractions(boletoService);
        verifyNoMoreInteractions(associadoRepository);
    }

    @Test
    void erroDocumentoInvalidoAoAlterarAssociado() {
        var associadoRequest = criarAssociadoRequest(PF);
        associadoRequest.setDocumento(CPF + "A");
        var associado = criarAssociados().get(0);
        doReturn(Optional.of(associado)).when(associadoRepository).findById(ID_ASSOCIADO);
        var exception = assertThrows(FormatoDocumentoInvalidoException.class, () -> target.alterarAssociado(ID_ASSOCIADO, associadoRequest));
        assertEquals("O documento informado não é válido", exception.getMessage());
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verifyNoInteractions(boletoService);
        verifyNoMoreInteractions(associadoRepository);
    }

    @Test
    void erroTipoPessoaInconsistenteAoAlterarAssociado() {
        var associadoRequest = criarAssociadoRequest(PJ);
        associadoRequest.setDocumento(CPF);
        var associado = criarAssociados().get(0);
        doReturn(Optional.of(associado)).when(associadoRepository).findById(ID_ASSOCIADO);
        var exception = assertThrows(TipoPessoaInconsistenteException.class, () -> target.alterarAssociado(ID_ASSOCIADO, associadoRequest));
        assertEquals("Tipo de documento não bate com o tipo pessoa informado", exception.getMessage());
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verifyNoInteractions(boletoService);
        verifyNoMoreInteractions(associadoRepository);
    }

    @Test
    void alterarAssociado() {
        var associadoRequest = criarAssociadoRequest(PJ);
        associadoRequest.setNome("Nome alterado");
        var associado = criarAssociados().get(0);
        var associadoAlterado = criarAssociado(associadoRequest);
        associadoAlterado.setId(associado.getId());
        var associadoDTO = criarAssociadoDTO(associadoAlterado);
        doReturn(Optional.of(associado)).when(associadoRepository).findById(ID_ASSOCIADO);
        doReturn(associadoAlterado).when(associadoRepository).save(refEq(associadoAlterado, "id"));

        var actual = target.alterarAssociado(ID_ASSOCIADO, associadoRequest);

        assertEquals(associadoDTO, actual);
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verify(associadoRepository).save(refEq(associadoAlterado, "id"));
        verifyNoInteractions(boletoService);
    }

    @Test
    void erroAssociadoNaoEncontradoAoDeletarAssociado() {
        doReturn(Optional.empty()).when(associadoRepository).findById(ID_ASSOCIADO);
        var exception = assertThrows(AssociadoNaoEncontradoException.class, () -> target.deletarAssociado(ID_ASSOCIADO));
        assertEquals("Não foi possível encontrar um associado para o ID: " + ID_ASSOCIADO, exception.getMessage());
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verifyNoInteractions(boletoService);
        verifyNoMoreInteractions(associadoRepository);
    }

    @Test
    void erroBoletoEmAbertoAoDeletarAssociado() {
        var associado = criarAssociados().get(0);
        associado.setId(ID_ASSOCIADO);
        doReturn(Optional.of(associado)).when(associadoRepository).findById(ID_ASSOCIADO);
        doReturn(true).when(boletoService).possuiBoletoAPagar(ID_ASSOCIADO);
        var exception = assertThrows(BoletoEmAbertoException.class, () -> target.deletarAssociado(ID_ASSOCIADO));
        assertEquals("Não é possível deletar um associado com boleto em aberto", exception.getMessage());
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verify(boletoService).possuiBoletoAPagar(ID_ASSOCIADO);
        verifyNoMoreInteractions(associadoRepository);
    }

    @Test
    void deletarAssociado() {
        var associado = criarAssociados().get(0);
        associado.setId(ID_ASSOCIADO);
        doReturn(Optional.of(associado)).when(associadoRepository).findById(ID_ASSOCIADO);
        doReturn(false).when(boletoService).possuiBoletoAPagar(ID_ASSOCIADO);
        target.deletarAssociado(ID_ASSOCIADO);
        verify(associadoRepository).findById(ID_ASSOCIADO);
        verify(boletoService).possuiBoletoAPagar(ID_ASSOCIADO);
        verify(associadoRepository).delete(associado);
    }

    @Test
    void validaDocumento(){
        var associado = criarAssociados().get(0);
        associado.setDocumento(CPF);
        boolean eValido = target.validaDocumento(associado.getDocumento());
        assertTrue(eValido);

    }

}