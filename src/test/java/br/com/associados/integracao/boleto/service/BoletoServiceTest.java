package br.com.associados.integracao.boleto.service;

import static org.mockito.Mockito.doReturn;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.associados.integracao.boleto.client.BoletoClient;

@ExtendWith(MockitoExtension.class)
class BoletoServiceTest {

    @Mock
    private BoletoClient boletoClient;

    @InjectMocks
    private BoletoService target;

    @Test
    void boletoNaoEncontradoAoConsultarBoletoPorAssociado() {
        doReturn(List.of()).when(boletoClient).consultarBoletos(ID_ASSOCIADO);
    }

}