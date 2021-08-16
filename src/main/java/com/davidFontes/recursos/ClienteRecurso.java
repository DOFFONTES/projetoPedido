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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.davidFontes.dominio.Cliente;
import com.davidFontes.dto.ClienteDTO;
import com.davidFontes.dto.ClienteNewDTO;
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
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> buscarTodos() {
		List<Cliente> lista = servico.buscarTodos();
		List<ClienteDTO> listaDTO = lista.stream()
				.map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		for(ClienteDTO li: listaDTO) {
			li.add(linkTo(methodOn(this.getClass()).buscar(li.getId())).withSelfRel());
		}		
		return new ResponseEntity<List<ClienteDTO>>(listaDTO, HttpStatus.OK);		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(path = "/pagina")
	public ResponseEntity<Page<ClienteDTO>> buscarPaginas(
			@RequestParam(value="pagina", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="direction", defaultValue = "nome")String direction, 
			@RequestParam(value="orderBy", defaultValue = "ASC")String orderBy) {
		Page<Cliente> lista = servico.findPage(page, linesPerPage, direction, orderBy);
		Page<ClienteDTO> listaDTO = lista.map(obj -> new ClienteDTO(obj));
			
		return ResponseEntity.ok().body(listaDTO);	
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
		Cliente obj = servico.fromDTO(objDto);
		obj = servico.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody ClienteDTO objDto){
		Cliente obj =servico.fromDTO(objDto);
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
	
	@PostMapping("/{picture}")
	public ResponseEntity<Void> uploadPicture(@RequestParam(name="file") MultipartFile file){	
		URI uri = servico.carregaFotoPerfil(file);	
		return ResponseEntity.created(uri).build();
	}
}
