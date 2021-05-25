package com.davidFontes.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.davidFontes.dominio.ItemPedido;
import com.davidFontes.dominio.ItemPedidoPK;

@Repository
public interface ItemPedidoRepositorio extends JpaRepository<ItemPedido, ItemPedidoPK> {

}
