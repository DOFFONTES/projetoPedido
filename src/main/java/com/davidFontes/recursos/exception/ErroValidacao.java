package com.davidFontes.recursos.exception;

import java.util.ArrayList;
import java.util.List;

public class ErroValidacao extends ErroPadrao {
	private static final long serialVersionUID = 1L;
	
	List<MensagemDeCampo> erros = new ArrayList<>();
	
	public ErroValidacao(Integer status, String msg, Long tempo) {
		super(status, msg, tempo);
	}

	public List<MensagemDeCampo> getErros() {
		return erros;
	}

	public void addErro(String nomeDoCampo, String mensagem) {
		erros.add(new MensagemDeCampo(nomeDoCampo, mensagem));
	}
	
}
