package com.davidFontes.servicos;

import org.springframework.mail.SimpleMailMessage;

import com.davidFontes.dominio.Pedido;

public interface EmailServico {

	void enviarEmailConfirmacaoPedido(Pedido obj);
	
	void enviarEmail(SimpleMailMessage msg);
}
