package com.davidFontes.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Cliente;
import com.davidFontes.dto.ClienteDTO;
import com.davidFontes.repositorios.ClienteRepositorio;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;
import com.davidFontes.servicos.exception.ViolacaoDeRestricaoDeIntegridadeException;

@Service
public class ClienteServico {
	
	@Autowired
	ClienteRepositorio repo;
	
	public Cliente buscar(Integer id) {
		 Optional<Cliente> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
		 } 
	
	public List<Cliente> buscarTodos(){
		List<Cliente> lista = repo.findAll();
		
		return lista;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		atualizaDado(newObj, obj);
		obj = repo.save(newObj);
		
		return obj;
	}
	
	public void delete(int id) {
		buscar(id);
		
		try {
			repo.deleteById(id);
			
		}catch (DataIntegrityViolationException e) {
			throw new ViolacaoDeRestricaoDeIntegridadeException("Não é possível exluir porque há entidades relacionadas");
		}
		
	}
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Page<Cliente> buscarPagina(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void atualizaDado(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
