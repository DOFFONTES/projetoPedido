package com.davidFontes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidFontes.dominio.Estado;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer> {

}
