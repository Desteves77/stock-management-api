package br.com.estoque.dto.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados necessários para registrar um novo usuário")
public class RegisterRequestDTO {
	
	@Schema(description = "Nome do usuário", example = "Davi Esteves")
	@NotBlank(message = "Nome obrigatório")
	private String nome; 
	
	@Schema(description = "Email usado para login", example = "davi@email.com")
	@Email(message = "Email deve ser válido")
	@NotBlank(message = "Email é obrigatório")
	private String email;
	
    @Schema(description = "Senha do usuário com no mínimo 8 caracteres", example = "12345678")
	@Size(min = 8, message = "Senha precisa ter no mínimo 8 caractéres")
	@NotBlank(message = "Senha é obrigatória")
	private String senha;
	
	
	
	
public RegisterRequestDTO() {
	
}
	
public RegisterRequestDTO(String nome, String email, String senha) {
	this.nome= nome;
	this.email= email;
	this.senha= senha;
}

public String getNome() {
	return nome;
}

public String getSenha() {
	return senha;
}

public String getEmail() {
	return email;
}


public void setNome(String nome) {
	this.nome= nome;
}

public void setSenha(String senha) {
	this.senha= senha;
}

public void setEmail(String email) {
	this.email= email;	
}




}