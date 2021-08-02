package com.davidFontes.servicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.dominio.Cidade;
import com.davidFontes.dominio.Cliente;
import com.davidFontes.dominio.Endereco;
import com.davidFontes.dominio.Estado;
import com.davidFontes.dominio.ItemPedido;
import com.davidFontes.dominio.Pagamento;
import com.davidFontes.dominio.PagamentoComBoleto;
import com.davidFontes.dominio.PagamentoComCartao;
import com.davidFontes.dominio.Pedido;
import com.davidFontes.dominio.Produto;
import com.davidFontes.dominio.enums.EstadoPagamento;
import com.davidFontes.dominio.enums.TipoCliente;
import com.davidFontes.repositorios.CategoriaRepositorio;
import com.davidFontes.repositorios.CidadeRepositorio;
import com.davidFontes.repositorios.ClienteRepositorio;
import com.davidFontes.repositorios.EnderecoRepositorio;
import com.davidFontes.repositorios.EstadoRepositorio;
import com.davidFontes.repositorios.ItemPedidoRepositorio;
import com.davidFontes.repositorios.PagamentoRepositorio;
import com.davidFontes.repositorios.PedidoRepositorio;
import com.davidFontes.repositorios.ProdutoRepositorio;

@Service
public class DBServico {

	@Autowired
	private CategoriaRepositorio categoriaRepositorio;	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	@Autowired
	private EstadoRepositorio estadoRepositorio;
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private PedidoRepositorio pedidoRepositorio;
	@Autowired
	private PagamentoRepositorio pagamentoRepositorio;
	@Autowired
	private ItemPedidoRepositorio itemPedidoRepositorio;
	
	public void instanciaTesteBancoDeDados() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Informática");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritorio", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().add(p11);
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p4.getCategorias().add(cat1);
		p5.getCategorias().add(cat1);
		p6.getCategorias().add(cat1);
		p7.getCategorias().add(cat1);
		p8.getCategorias().add(cat1);
		p9.getCategorias().add(cat1);
		p10.getCategorias().add(cat1);
		p11.getCategorias().add(cat1);
		
		categoriaRepositorio.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));	
		produtoRepositorio.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepositorio.saveAll(Arrays.asList(est1,est2));
		cidadeRepositorio.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "938338393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
	
		clienteRepositorio.save(cli1);
		enderecoRepositorio.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"),cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"),cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE,
							ped2, sdf.parse("20/10/1017 00:00"), null);
		
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepositorio.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepositorio.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().add(ip3);
		
		p1.getItens().add(ip1);
		p2.getItens().add(ip3);
		p3.getItens().add(ip2);
		
		itemPedidoRepositorio.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
}
