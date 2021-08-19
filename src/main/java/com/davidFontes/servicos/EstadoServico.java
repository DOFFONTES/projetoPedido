package com.davidFontes.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Estado;
import com.davidFontes.repositorios.EstadoRepositorio;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;

@Service
public class EstadoServico {

	@Autowired
	EstadoRepositorio repo;
	
public Estado buscar(Integer id) {
		
		/*UserSS user = UserServico.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AutorizacaoException("Acesso negado");
		}*/
	
		 Optional<Estado> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Estado.class.getName())); 
		 } 
	
	public List<Estado> buscarTodos(){
		
		return repo.findAllByOrderByNome();
	}
}
