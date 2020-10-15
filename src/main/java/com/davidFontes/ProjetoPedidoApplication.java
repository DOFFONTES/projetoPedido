package com.davidFontes;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.repositorios.CategoriaRepositorio;

@SpringBootApplication
public class ProjetoPedidoApplication implements CommandLineRunner {
	
	@Autowired
	CategoriaRepositorio categoriaRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(ProjetoPedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		categoriaRepositorio.saveAll(Arrays.asList(cat1, cat2));
	}

}
