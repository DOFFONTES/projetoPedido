package com.davidFontes.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.davidFontes.dominio.Cliente;
import com.davidFontes.dominio.Pedido;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> {

	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
