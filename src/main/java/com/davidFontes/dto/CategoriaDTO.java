package com.davidFontes.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.davidFontes.dominio.Categoria;

public class CategoriaDTO extends RepresentationModel<Categoria> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
	}

	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
}
