package br.com.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description= "Representa dados para criar um insumo")
public class InsumoCreateDTO {

	@Schema(description="Nome de um insumo", example = "InsumoTeste1", requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message= "Nome obrigatório")
	private String nome;
	
	@Schema(description = "Quantidade de um insumo", minimum= "0", example = "1", requiredMode = Schema.RequiredMode.REQUIRED )
	@NotNull(message= "Quantidade obrigatória")
	@PositiveOrZero(message= "Quantidade não pode ser negativa")
	private Integer quantidade;
	
	
public InsumoCreateDTO() {
	
}
	

public InsumoCreateDTO(String nome, Integer quantidade) {
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
