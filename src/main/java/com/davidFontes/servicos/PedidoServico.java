package com.davidFontes.servicos;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Cliente;
import com.davidFontes.dominio.ItemPedido;
import com.davidFontes.dominio.PagamentoComBoleto;
import com.davidFontes.dominio.Pedido;
import com.davidFontes.dominio.enums.EstadoPagamento;
import com.davidFontes.repositorios.ItemPedidoRepositorio;
import com.davidFontes.repositorios.PagamentoRepositorio;
import com.davidFontes.repositorios.PedidoRepositorio;
import com.davidFontes.security.UserSS;
import com.davidFontes.servicos.exception.AutorizacaoException;
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
	private ClienteServico clienteServico;
	
	@Autowired
	private ItemPedidoRepositorio itemPedidoRepositorio;
	
	@Autowired
	private EmailServico emailServico;
	
	public Pedido buscar(Integer id) {
		 Optional<Pedido> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); 
		 }
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteServico.buscar(obj.getCliente().getId()));
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
			ip.setProduto(produtoServico.buscar(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepositorio.saveAll(obj.getItens());
		
		emailServico.enviarEmailHTMLConfirmaçãoPedido(obj);
		//System.out.println(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserServico.authenticated();
		if (user == null) {
			throw new AutorizacaoException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteServico.buscar(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}
