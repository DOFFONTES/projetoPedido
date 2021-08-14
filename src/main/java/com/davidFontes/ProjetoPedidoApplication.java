package com.davidFontes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.davidFontes.servicos.S3Servico;

@SpringBootApplication
public class ProjetoPedidoApplication implements CommandLineRunner {

	@Autowired
	private S3Servico s3Servico;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoPedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		s3Servico.uploadFile("C:\\Users\\dof_f\\OneDrive\\Imagens\\Capturas de tela\\testando.png");
	}

}
