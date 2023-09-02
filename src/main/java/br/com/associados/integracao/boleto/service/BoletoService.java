package br.com.associados.integracao.boleto.service;

import br.com.associados.integracao.boleto.client.BoletoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class BoletoService {

    @Autowired
    private BoletoClient boletoClient;

    public boolean possuiBoletoAPagar(String idAssociado) {
        var boletos = boletoClient.consultarBoletos(idAssociado);
        if(!CollectionUtils.isEmpty(boletos)){
            return boletos.stream().anyMatch(b -> b.isEmAberto() == true);
        }
        return false;
    }
}
