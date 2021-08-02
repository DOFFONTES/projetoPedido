package com.davidFontes.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.davidFontes.servicos.DBServico;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	DBServico dbServico;

	@Bean
	public boolean instanciaBancoDeDados() throws ParseException {
		
		dbServico.instanciaTesteBancoDeDados();
		
		return true;
	}
}
