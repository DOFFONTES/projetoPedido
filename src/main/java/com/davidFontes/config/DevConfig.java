package com.davidFontes.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.davidFontes.servicos.DBServico;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	DBServico dbServico;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instanciaBancoDeDados() throws ParseException {
		
		if(!"create".equals(strategy)) {
			return false;
		}			
		
		dbServico.instanciaTesteBancoDeDados();
		
		return true;
	}
}
