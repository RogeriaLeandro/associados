package br.com.associados.integracao.boleto.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoletoDTOTest {


    @Test
    void getIdAssociado() {
        BoletoDTO bx = new BoletoDTO();

        bx.setIdAssociado("1");
        bx.setEmAberto(true);

        assertTrue(bx.getIdAssociado().equals("1"));
        //assertTrue(bx.equals("1"));

    }
}