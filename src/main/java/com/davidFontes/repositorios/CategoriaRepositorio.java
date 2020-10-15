package com.davidFontes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidFontes.dominio.Categoria;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {

}
