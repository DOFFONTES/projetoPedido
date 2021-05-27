package com.davidFontes.servicos.exception;

public class ViolacaoDeRestricaoDeIntegridadeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ViolacaoDeRestricaoDeIntegridadeException(String msg) {
		super(msg);
	}
	
	public ViolacaoDeRestricaoDeIntegridadeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
