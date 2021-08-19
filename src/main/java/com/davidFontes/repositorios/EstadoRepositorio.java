package com.davidFontes.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.davidFontes.dominio.Estado;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer> {

	 @Transactional(readOnly=true)
	 List<Estado> findAllByOrderByNome();
}
