package br.com.estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description= "Representa dados para atualizar um insumo")
public class InsumoPutDTO {
	
	@Schema(description= "Nome de um insumo", example = "InsumoTeste",  requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message= "Nome obrigatório")
	private String nome;
	
	@Schema(description = "Quantidade de um insumo", minimum= "0", example = "10", requiredMode = Schema.RequiredMode.REQUIRED )
	@NotNull(message= "Quantidade obrigatória")
	@PositiveOrZero(message= "Quantidade não pode ser negativa")
	private Integer quantidade;
	
	
public InsumoPutDTO() {
	
}
	

public InsumoPutDTO(String nome, Integer quantidade) {
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
