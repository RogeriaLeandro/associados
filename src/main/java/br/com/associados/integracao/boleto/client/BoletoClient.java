package br.com.associados.integracao.boleto.client;

import br.com.associados.integracao.boleto.dto.BoletoDTO;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Boleto-Api", url = "${integracao.boleto-api.url}")
public interface BoletoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/boletos/{idAssociado}")
    List<BoletoDTO> consultarBoletos(@PathVariable(value = "idAssociado") String idAssociado);
}
