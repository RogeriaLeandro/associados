package br.com.associados.exceptions;

public class DocumentoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DocumentoInvalidoException(String mensagem) {
		super(mensagem);
	}
	
}