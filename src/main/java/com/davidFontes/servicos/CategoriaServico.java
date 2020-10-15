package com.davidFontes.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.repositorios.CategoriaRepositorio;

@Service
public class CategoriaServico {
	
	@Autowired
	CategoriaRepositorio repo;
	
	public Categoria buscar(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		 return obj.orElse(null);
		 } 



}
