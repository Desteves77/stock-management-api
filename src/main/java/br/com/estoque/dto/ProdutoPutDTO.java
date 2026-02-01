package br.com.estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description= "Representa dados para atualizar um produto")
public class ProdutoPutDTO {

	@Schema(description = "Nome de um produto", example = "ProdutoTeste", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message= "Nome obrigatório")
	private String nome; 
	
	@Schema(description = "Quantidade de um produto", minimum = "0", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message= "Quantidade obrigatória")
	@PositiveOrZero(message= "Quantidade não pode ser negativa")
	private Integer quantidade;
	
	public ProdutoPutDTO() {
		
	}
	
	public ProdutoPutDTO(String nome, Integer quantidade) {
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
