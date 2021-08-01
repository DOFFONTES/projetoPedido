package com.davidFontes.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.dominio.Produto;
import com.davidFontes.repositorios.CategoriaRepositorio;
import com.davidFontes.repositorios.ProdutoRepositorio;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;

@Service
public class ProdutoServico {
	
	@Autowired
	ProdutoRepositorio repo;
	
	@Autowired
	CategoriaRepositorio categoriaRepositorio;
	
	public Produto buscar(Integer id) {
		 Optional<Produto> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName())); 
		 } 

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepositorio.findAllById(ids);
		return repo.findDistinctByNomeIgnoreCaseContainingAndCategoriasIn(nome, categorias, pageRequest);
	}

}
