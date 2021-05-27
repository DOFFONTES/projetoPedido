package com.davidFontes.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidFontes.dominio.Cliente;
import com.davidFontes.servicos.ClienteServico;

@RestController
@RequestMapping(value="/clientes")
public class ClienteRecurso {
	
	@Autowired
	private ClienteServico servico;
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable("id") Integer id) {
		Cliente obj = servico.buscar(id);
		
		return ResponseEntity.ok().body(obj);		
	}
}
