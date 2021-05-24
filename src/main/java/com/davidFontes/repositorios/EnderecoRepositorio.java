package com.davidFontes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidFontes.dominio.Endereco;

@Repository
public interface EnderecoRepositorio extends JpaRepository<Endereco, Integer> {

}
