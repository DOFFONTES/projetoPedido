package com.davidFontes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidFontes.dominio.Pagamento;

@Repository
public interface PagamentoRepositorio extends JpaRepository<Pagamento, Integer> {

}
