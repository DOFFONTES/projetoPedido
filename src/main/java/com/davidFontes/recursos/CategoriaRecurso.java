package com.davidFontes.recursos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.servicos.CategoriaServico;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaRecurso {
	
	@Autowired
	private CategoriaServico servico;
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscar(@PathVariable("id") Integer id) {
		Categoria obj = servico.buscar(id);
		
		obj.add(linkTo(methodOn(this.getClass()).buscar(id)).withSelfRel());
		return ResponseEntity.ok().body(obj);		
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = servico.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		//obj.add(linkTo(methodOn(this.getClass()).buscar(obj.getId())).withRel("Link da Categoria"));
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Categoria obj){
		obj.setId(id);
		obj = servico.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		
		servico.delete(id);
		
		return ResponseEntity.noContent().build();		
	}
}
