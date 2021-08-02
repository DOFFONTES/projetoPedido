package com.davidFontes.recursos;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.dominio.Pedido;
import com.davidFontes.dto.CategoriaDTO;
import com.davidFontes.servicos.PedidoServico;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoRecurso {
	
	@Autowired
	private PedidoServico servico;
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscar(@PathVariable("id") Integer id) {
		Pedido obj = servico.buscar(id);
		
		return ResponseEntity.ok().body(obj);		
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){
		obj = servico.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
