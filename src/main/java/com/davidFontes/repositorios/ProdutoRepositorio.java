package com.davidFontes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidFontes.dominio.Produto;

@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Integer> {

}
