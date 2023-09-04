package br.com.associados.integracao.boleto.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoletoDTO {

    private String idAssociado;

    private boolean emAberto;
}
