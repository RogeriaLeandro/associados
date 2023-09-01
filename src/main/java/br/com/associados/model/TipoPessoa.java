package br.com.associados.model;

public enum TipoPessoa {
    
    PJ("PJ"),
    PF("PF");

    private String descricaoTipoPessoa;

    TipoPessoa(String descricaoTipoPessoa) {
        this.descricaoTipoPessoa = descricaoTipoPessoa;
    }

    public String getDescricaoTipoPessoa() {
        return descricaoTipoPessoa;
    }

}
