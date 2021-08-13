package com.davidFontes.recursos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.dto.CategoriaDTO;
import com.davidFontes.servicos.CategoriaServico;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaRecurso {
	
	@Autowired
	private CategoriaServico servico;
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscar(@PathVariable("id") Integer id) {
		Categoria obj = servico.buscar(id);
		
		obj.add(linkTo(methodOn(this.getClass()).buscarTodos()).withRel("Lista de Categorias"));
		return ResponseEntity.ok().body(obj);		
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> buscarTodos() {
		List<Categoria> lista = servico.buscarTodos();
		List<CategoriaDTO> listaDTO = lista.stream()
				.map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		for(CategoriaDTO li: listaDTO) {
			li.add(linkTo(methodOn(this.getClass()).buscar(li.getId())).withSelfRel());
		}		
		return new ResponseEntity<List<CategoriaDTO>>(listaDTO, HttpStatus.OK);		
	}
	
	@GetMapping(path = "/pagina")
	public ResponseEntity<Page<CategoriaDTO>> buscarPaginas(
			@RequestParam(value="pagina", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="direction", defaultValue = "nome")String direction, 
			@RequestParam(value="orderBy", defaultValue = "ASC")String orderBy) {
		Page<Categoria> lista = servico.findPage(page, linesPerPage, direction, orderBy);
		Page<CategoriaDTO> listaDTO = lista.map(obj -> new CategoriaDTO(obj));
		for(CategoriaDTO li: listaDTO) {
			li.add(linkTo(methodOn(this.getClass()).buscar(li.getId())).withSelfRel());
		}	
			
		return ResponseEntity.ok().body(listaDTO);	
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){
		Categoria obj = servico.fromDTO(objDto);
		obj = servico.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody CategoriaDTO objDto){
		Categoria obj =servico.fromDTO(objDto);
		obj.setId(id);
		obj = servico.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		
		servico.delete(id);
		
		return ResponseEntity.noContent().build();		
	}
}
