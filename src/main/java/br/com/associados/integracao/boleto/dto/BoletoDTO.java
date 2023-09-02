package br.com.associados.integracao.boleto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoletoDTO {
    private String idAssociado;
    private boolean emAberto;
}
