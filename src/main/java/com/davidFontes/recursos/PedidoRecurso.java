package com.davidFontes.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidFontes.dominio.Pedido;
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
}
