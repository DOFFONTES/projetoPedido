package com.davidFontes.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.dto.CategoriaDTO;
import com.davidFontes.repositorios.CategoriaRepositorio;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;
import com.davidFontes.servicos.exception.ViolacaoDeRestricaoDeIntegridadeException;

@Service
public class CategoriaServico {
	
	@Autowired
	CategoriaRepositorio repo;
	
	public Categoria buscar(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
		 } 
	public List<Categoria> buscarTodos(){
		List<Categoria> lista = repo.findAll();
		
		return lista;
	}

	public Categoria insert(Categoria obj) {
		obj = repo.save(obj);		
		return obj;
	}

	public Categoria update(Categoria obj) {
		Categoria newObj = buscar(obj.getId());
		atualizaDado(newObj, obj);
		obj = repo.save(newObj);
		
		return obj;
	}
	
	public void delete(int id) {
		buscar(id);
		
		try {
			repo.deleteById(id);
			
		}catch (DataIntegrityViolationException e) {
			throw new ViolacaoDeRestricaoDeIntegridadeException("Não é possível exluir uma categoria que possui produtos");
		}
		
	}
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Page<Categoria> buscarPagina(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	private void atualizaDado(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}
