package com.davidFontes.recursos.exception;

import java.io.Serializable;

public class MensagemDeCampo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeDoCampo;
	private String mensagem;
	
	public MensagemDeCampo() {		
	}

	public MensagemDeCampo(String nomeDoCampo, String mensagem) {
		super();
		this.nomeDoCampo = nomeDoCampo;
		this.mensagem = mensagem;
	}

	public String getNomeDoCampo() {
		return nomeDoCampo;
	}

	public void setNomeDoCampo(String nomeDoCampo) {
		this.nomeDoCampo = nomeDoCampo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
