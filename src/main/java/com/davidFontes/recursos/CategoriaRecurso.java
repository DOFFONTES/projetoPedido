package com.davidFontes.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.servicos.CategoriaServico;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaRecurso {
	
	@Autowired
	private CategoriaServico servico;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Integer id) {
		Categoria obj = servico.buscar(id);
		
		return ResponseEntity.ok().body(obj);		
	}
}
