package br.com.estoque.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Schema(description= "Representa um insumo cadastrado no sistema")
@Entity
public class Insumo {
	
	@Schema(description= "Identificador de um insumo", example= "1")
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description= "Nome de um insumo", example = "Insumo Teste",  requiredMode = Schema.RequiredMode.REQUIRED)
	private String nome;
	@Schema(description= "Quantidade de um insumo", minimum = "0", example = "10" , requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer quantidade;
	
	
public Insumo(){
	
}

public Insumo(String nome, Integer quantidade) {
	this.nome= nome;
	this.quantidade= quantidade;
	
}

public Insumo(Long id, String nome, Integer quantidade) {
	this.id= id;
	this.nome= nome;
	this.quantidade= quantidade;
}

public void setNome(String nome) {
	this.nome= nome;
}

public void setQuantidade(Integer quantidade) {
	this.quantidade= quantidade;
	
}

public void setId(Long id) {
	this.id= id;
}

public String getNome() {
	return nome;
}

public Integer getQuantidade() {
	return quantidade;
}

public Long getId() {
	return id;
}
}
