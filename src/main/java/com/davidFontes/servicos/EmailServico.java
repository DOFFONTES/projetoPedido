package com.davidFontes.servicos;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.davidFontes.dominio.Cliente;
import com.davidFontes.dominio.Pedido;

public interface EmailServico {

	void enviarEmailConfirmacaoPedido(Pedido obj);
	
	void enviarEmail(SimpleMailMessage msg);
	
	void enviarEmailHTMLConfirmaçãoPedido(Pedido obj);
	
	void enviarHtmlEmail(MimeMessage msg);
	
	void enviarNovaSenhaEmail(Cliente cliente, String newPass);
}
