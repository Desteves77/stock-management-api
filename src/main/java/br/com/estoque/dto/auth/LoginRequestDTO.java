package br.com.estoque.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados necessários para autenticação do usuário")

public class LoginRequestDTO {
	
    @Schema(description = "Email cadastrado do usuário", example = "davi@email.com")
	@Email(message = "Email deve ser válido")
	@NotBlank(message = "Email é obrigatório")
	private String email;
	
    @Schema(description = "Senha do usuário", example = "12345678")
	@NotBlank(message = "Senha é obrigatória")
	private String senha;
	
	
	
	
public LoginRequestDTO() {
	
}
	
public LoginRequestDTO(String email, String senha) {
	this.email= email;
	this.senha= senha;
}

public String getSenha() {
	return senha;
}

public String getEmail() {
	return email;
}

public void setSenha(String senha) {
	this.senha= senha;
}

public void setEmail(String email) {
	this.email= email;	
}

	
}
