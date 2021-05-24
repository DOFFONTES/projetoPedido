package com.davidFontes;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.davidFontes.dominio.Categoria;
import com.davidFontes.dominio.Cidade;
import com.davidFontes.dominio.Cliente;
import com.davidFontes.dominio.Endereco;
import com.davidFontes.dominio.Estado;
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
import com.davidFontes.repositorios.PagamentoRepositorio;
import com.davidFontes.repositorios.PedidoRepositorio;
import com.davidFontes.repositorios.ProdutoRepositorio;

@SpringBootApplication
public class ProjetoPedidoApplication implements CommandLineRunner {
	
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

	public static void main(String[] args) {
		SpringApplication.run(ProjetoPedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().add(cat1);
		
		categoriaRepositorio.saveAll(Arrays.asList(cat1, cat2));	
		produtoRepositorio.saveAll(Arrays.asList(p1, p2, p3));
		
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
		
	}

}
