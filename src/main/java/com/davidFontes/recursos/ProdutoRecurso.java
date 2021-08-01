package com.davidFontes.recursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.davidFontes.dominio.Produto;
import com.davidFontes.dto.ProdutoDTO;
import com.davidFontes.recursos.utilitarios.URL;
import com.davidFontes.servicos.ProdutoServico;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoRecurso {
	
	@Autowired
	private ProdutoServico servico;
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscar(@PathVariable("id") Integer id) {
		Produto obj = servico.buscar(id);
		
		return ResponseEntity.ok().body(obj);		
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> buscarPagina(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue = "") String categorias,
			@RequestParam(value="pagina", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="direction", defaultValue = "nome")String direction, 
			@RequestParam(value="orderBy", defaultValue = "ASC")String orderBy) {
		String nomeDecode = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> lista = servico.search(nomeDecode, ids, page, linesPerPage, direction, orderBy);
		Page<ProdutoDTO> listaDTO = lista.map(obj -> new ProdutoDTO(obj));
			
		return ResponseEntity.ok().body(listaDTO);	
	}
}
