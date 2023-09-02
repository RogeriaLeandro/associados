package br.com.associados.exceptions;

public class BoletoEmAbertoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BoletoEmAbertoException(String mensagem) {
		super(mensagem);
	}
	
}