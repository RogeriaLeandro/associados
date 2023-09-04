package br.com.associados.model; 

public enum TipoPessoa {
    
    PJ("PJ", 14),
    PF("PF", 11);

    private int sizeDoc;
    private String descricaoTipoPessoa;

    TipoPessoa(String descricaoTipoPessoa, int sizeDoc) {
        this.descricaoTipoPessoa = descricaoTipoPessoa;
        this.sizeDoc = sizeDoc;
    }

    public int getSizeDoc() {
        return sizeDoc;
    }
}
