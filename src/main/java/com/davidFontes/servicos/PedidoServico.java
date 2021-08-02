package com.davidFontes.servicos;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.ItemPedido;
import com.davidFontes.dominio.PagamentoComBoleto;
import com.davidFontes.dominio.Pedido;
import com.davidFontes.dominio.enums.EstadoPagamento;
import com.davidFontes.repositorios.ItemPedidoRepositorio;
import com.davidFontes.repositorios.PagamentoRepositorio;
import com.davidFontes.repositorios.PedidoRepositorio;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;

@Service
public class PedidoServico {
	
	@Autowired
	PedidoRepositorio repo;
	
	@Autowired
	private ProdutoServico produtoServico;
	
	@Autowired
	private BoletoServico boletoServico;
	
	@Autowired
	private PagamentoRepositorio pagamentoRepositorio;
	
	@Autowired
	private ItemPedidoRepositorio itemPedidoRepositorio;
	
	public Pedido buscar(Integer id) {
		 Optional<Pedido> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
		 }
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			
			boletoServico.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepositorio.save(obj.getPagamento());
		
		for (ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoServico.buscar(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepositorio.saveAll(obj.getItens());
		return obj;
	}



}
