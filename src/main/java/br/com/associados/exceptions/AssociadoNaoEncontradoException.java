package br.com.associados.exceptions;

public class AssociadoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AssociadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
}