package br.com.associados.exceptions;

public class FormatoDocumentoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FormatoDocumentoInvalidoException(String mensagem) {
		super(mensagem);
	}
	
}