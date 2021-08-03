package com.davidFontes.servicos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.davidFontes.dominio.Pedido;

public abstract class AbstractEmailService implements EmailServico {

	@Value("${default.sender}")
	private String sender;
			
	@Override
	public void enviarEmailConfirmacaoPedido(Pedido obj) {
		SimpleMailMessage sm = preparaEmailDoPedido(obj);
		enviarEmail(sm);
	}

	protected SimpleMailMessage preparaEmailDoPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado:" + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		
		return sm;
	}
}
