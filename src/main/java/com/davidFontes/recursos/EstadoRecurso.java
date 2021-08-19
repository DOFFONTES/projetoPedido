package com.davidFontes.recursos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidFontes.dominio.Cidade;
import com.davidFontes.dominio.Estado;
import com.davidFontes.dto.CidadeDTO;
import com.davidFontes.dto.EstadoDTO;
import com.davidFontes.servicos.CidadeServico;
import com.davidFontes.servicos.EstadoServico;

@RestController
@RequestMapping(value="/estados")
public class EstadoRecurso {
	
	@Autowired
	private EstadoServico servico;
	
	@Autowired
	private CidadeServico cidadeServico;

	@GetMapping
	public ResponseEntity<List<EstadoDTO>> buscarTodos() {
		List<Estado> lista = servico.buscarTodos();
		List<EstadoDTO> listaDTO = lista.stream()
				.map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		for(EstadoDTO li: listaDTO) {
			li.add(linkTo(methodOn(this.getClass()).buscar(li.getId())).withSelfRel());
		}		
		return new ResponseEntity<List<EstadoDTO>>(listaDTO, HttpStatus.OK);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable("id") Integer id) {
		Estado obj = servico.buscar(id);
		
		return ResponseEntity.ok().body(obj);		
	}
	
	@GetMapping("/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> buscaTodasCidades(@PathVariable("estadoId") Integer estadoId) {
		List<Cidade> lista = cidadeServico.bucaCidadesPorEstado(estadoId);
		List<CidadeDTO> listaDTO = lista.stream()
				.map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		//for(CidadeDTO li: listaDTO) {
		//	li.add(linkTo(methodOn(this.getClass()).buscar(li.getId())).withSelfRel());
		//}		
		return new ResponseEntity<List<CidadeDTO>>(listaDTO, HttpStatus.OK);		
	}
}
