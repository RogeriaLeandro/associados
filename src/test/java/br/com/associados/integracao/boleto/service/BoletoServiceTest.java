package br.com.associados.integracao.boleto.service;

import br.com.associados.exceptions.AssociadoNaoEncontradoException;
import br.com.associados.exceptions.BoletoEmAbertoException;
import br.com.associados.exceptions.FormatoDocumentoInvalidoException;
import br.com.associados.exceptions.TipoPessoaInconsistenteException;
import br.com.associados.integracao.boleto.client.BoletoClient;
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

import static br.com.associados.AssociadoTestHelper.*;
import static br.com.associados.model.TipoPessoa.PF;
import static br.com.associados.model.TipoPessoa.PJ;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoletoServiceTest {

    @Mock
    private BoletoClient boletoClient;

    @InjectMocks
    private BoletoService target;

    @Test
    void boletoNaoEncontradoAoConsultarBoletoPorAssociado() {
        doReturn(List.of()).when(boletoClient).consultarBoletos(ID_ASSOCIADO);
        assertFalse(target.possuiBoletoAPagar(ID_ASSOCIADO));
        verify(boletoClient).consultarBoletos(ID_ASSOCIADO);
    }

    @Test
    void semBoletosEmAbertoAoConsultarBoletosPorAssociado() {
        doReturn(criarBoletosTodosPagos(ID_ASSOCIADO)).when(boletoClient).consultarBoletos(ID_ASSOCIADO);
        assertFalse(target.possuiBoletoAPagar(ID_ASSOCIADO));
        verify(boletoClient).consultarBoletos(ID_ASSOCIADO);
    }

    @Test
    void boletoEmAbertoAoConsultarBoletoPorAssociado() {
        doReturn(criarBoletosUmAPagar(ID_ASSOCIADO)).when(boletoClient).consultarBoletos(ID_ASSOCIADO);
        assertTrue(target.possuiBoletoAPagar(ID_ASSOCIADO));
        verify(boletoClient).consultarBoletos(ID_ASSOCIADO);
    }

}