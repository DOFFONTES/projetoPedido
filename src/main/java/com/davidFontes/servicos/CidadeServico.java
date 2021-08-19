package com.davidFontes.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Cidade;
import com.davidFontes.repositorios.CidadeRepositorio;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;

@Service
public class CidadeServico {
	
	@Autowired
	CidadeRepositorio repo;
	
public Cidade buscar(Integer id) {
		
		/*UserSS user = UserServico.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AutorizacaoException("Acesso negado");
		}*/
	
		 Optional<Cidade> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName())); 
		 } 
	
	public List<Cidade> bucaCidadesPorEstado(Integer estadoId){
		
		return repo.findCidades(estadoId);
	}
	
}
