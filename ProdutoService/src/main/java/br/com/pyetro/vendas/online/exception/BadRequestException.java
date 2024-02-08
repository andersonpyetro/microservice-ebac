package br.com.pyetro.vendas.online.exception;

public class BadRequestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4202541292918665416L;
	
	public BadRequestException(String message) {
		super(message);
	}

}
