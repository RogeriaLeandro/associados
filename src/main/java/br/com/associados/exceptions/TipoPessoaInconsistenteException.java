package br.com.associados.exceptions;

public class TipoPessoaInconsistenteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TipoPessoaInconsistenteException(String mensagem) {
        super(mensagem);
    }
}
