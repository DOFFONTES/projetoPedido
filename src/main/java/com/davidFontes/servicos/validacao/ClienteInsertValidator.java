package com.davidFontes.servicos.validacao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.davidFontes.dominio.enums.TipoCliente;
import com.davidFontes.dto.ClienteNewDTO;
import com.davidFontes.recursos.exception.MensagemDeCampo;
import com.davidFontes.repositorios.ClienteRepositorio;
import com.davidFontes.servicos.validacao.utilitarios.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	ClienteRepositorio repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<MensagemDeCampo> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new MensagemDeCampo("cpfOuCnpj", "CPF inválido"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new MensagemDeCampo("cpfOuCnpj", "CNPJ inválido"));
		}	
		if(repo.findByEmail(objDto.getEmail()) != null){
			list.add(new MensagemDeCampo("email", "Email já existente"));
		}
		

		for (MensagemDeCampo e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeDoCampo())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}