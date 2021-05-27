package com.davidFontes.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.repositorios.CategoriaRepositorio;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;
import com.davidFontes.servicos.exception.ViolacaoDeRestricaoDeIntegridadeException;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class CategoriaServico {
	
	@Autowired
	CategoriaRepositorio repo;
	
	public Categoria buscar(Integer id) {
		 Optional<Categoria> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
		 } 

	public Categoria insert(Categoria obj) {
		obj = repo.save(obj);		
		return obj;
	}

	public Categoria update(Categoria obj) {
		buscar(obj.getId());
		obj = repo.save(obj);
		
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
}
