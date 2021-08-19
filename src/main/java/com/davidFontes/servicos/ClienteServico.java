package com.davidFontes.servicos;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.davidFontes.dominio.Cidade;
import com.davidFontes.dominio.Cliente;
import com.davidFontes.dominio.Endereco;
import com.davidFontes.dominio.enums.Perfil;
import com.davidFontes.dominio.enums.TipoCliente;
import com.davidFontes.dto.ClienteDTO;
import com.davidFontes.dto.ClienteNewDTO;
import com.davidFontes.repositorios.ClienteRepositorio;
import com.davidFontes.repositorios.EnderecoRepositorio;
import com.davidFontes.security.UserSS;
import com.davidFontes.servicos.exception.AutorizacaoException;
import com.davidFontes.servicos.exception.ObjetoNaoEncontradoException;
import com.davidFontes.servicos.exception.ViolacaoDeRestricaoDeIntegridadeException;

@Service
public class ClienteServico {
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepositorio repo;
	
	@Autowired
	private EnderecoRepositorio enderecoRepositorio;
	
	@Autowired
	private S3Servico s3Servico;
	
	@Autowired
	private ImagemServico imagemServico;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);	
		enderecoRepositorio.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente buscar(Integer id) {
		
		UserSS user = UserServico.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AutorizacaoException("Acesso negado");
		}
		
		 Optional<Cliente> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjetoNaoEncontradoException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
		 } 
	
	public List<Cliente> buscarTodos(){
		List<Cliente> lista = repo.findAll();
		
		return lista;
	}
	
	public Cliente findByEmail(String email) {
		UserSS user = UserServico.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AutorizacaoException("Acesso negado");
		}

		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjetoNaoEncontradoException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		atualizaDado(newObj, obj);
		obj = repo.save(newObj);
		
		return obj;
	}
	
	public void delete(int id) {
		buscar(id);
		
		try {
			repo.deleteById(id);
			
		}catch (DataIntegrityViolationException e) {
			throw new ViolacaoDeRestricaoDeIntegridadeException("Não é possível exluir porque há pedidos relacionadas");
		}
		
	}
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Page<Cliente> buscarPagina(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}
	
	private void atualizaDado(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI carregaFotoPerfil(MultipartFile multipartFile) {
		
		UserSS user = UserServico.authenticated();
		
		if(user == null) {
			throw new AutorizacaoException("Acesso negado");
		}
		
		BufferedImage jpgImage = imagemServico.getJpgImageFromFile(multipartFile);
		jpgImage = imagemServico.cropSquare(jpgImage);
		jpgImage = imagemServico.resize(jpgImage, size);
		
		String nomeArquivo = prefix + user.getId() + ".jpg";
		
		
		return s3Servico.uploadFile(imagemServico.getInputStream(jpgImage, "jpg"), nomeArquivo, "image");
	}
}
