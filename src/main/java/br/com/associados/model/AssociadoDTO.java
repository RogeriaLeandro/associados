package br.com.associados.model;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
public class AssociadoDTO {

	private String uuid;
    private String documento;
    private String tipoPessoa;
    private String nome;

    public AssociadoDTO convertAssociadoDTO(Associado associado) {
        BeanUtils.copyProperties(associado,
                                 this,
                                 "uuid",
                                 getRegexDocumento(this.documento, this.tipoPessoa),
                                 "tipoPessoa", 
                                 "nome");

        return this;
    }

    public String getRegexDocumento(String documento, String tipoPessoa) {
        String documentoRegex = "";
        if(tipoPessoa.equals("PF")) {
            documento.matches("/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/");
        } else {
            documento.matches("/^\d{2}\.\d{3}\.\d{3}\/\d{4}\-\d{2}$/");
        }

        return documentoRegex
    }

}
