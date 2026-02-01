package br.com.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description= "Representa dados para criar um produto")
public class ProdutoCreateDTO {
	
	@Schema(description = "Nome do produto", example = "Produto Teste", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message= "Nome é obrigatório")
	private String nome;
	
	@Schema(description = "Quantidade do produto", minimum = "0", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "Quantidade é obrigatória")
	@PositiveOrZero(message = "Quantidade não pode ser negativa")
	private Integer quantidade;
	
	
public ProdutoCreateDTO() {
	
}

public ProdutoCreateDTO(String nome, Integer quantidade) {
	this.nome= nome;
	this.quantidade= quantidade;
	
	
}

public String getNome() {
	return nome;
}

public Integer getQuantidade() {
	return quantidade;
}

public void setNome(String nome) {
	this.nome= nome;
}

public void setQuantidade(Integer quantidade) {
	this.quantidade= quantidade;
}











}