package br.com.estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "Representa dados para atualizar parcialmente um produto")
public class InsumoPatchDTO {
	
	@Schema(description = "Representa o nome do produto", example = "Produto Teste")
	@Size(min = 1, message = "Nome não pode ser vazio")
	private String nome;
	
	@Schema(description = "Quantidade do produto", minimum = "0", example = "10")
	@PositiveOrZero(message= "Quantidade não pode ser negativa")
	private Integer quantidade;
	
	
public InsumoPatchDTO() {
	
}
	

public InsumoPatchDTO(String nome, Integer quantidade) {
	this.nome= nome;
	this.quantidade= quantidade;

}

public String getNome() {return nome;}

public Integer getQuantidade() {return quantidade;}
	
public void setNome(String nome) {
	this.nome= nome;
}

public void setQuantidade(Integer quantidade) {
	this.quantidade= quantidade;
}
}
