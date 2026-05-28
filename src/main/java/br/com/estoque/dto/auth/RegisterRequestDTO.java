package br.com.estoque.dto.auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public class RegisterRequestDTO {
	
	@NotBlank(message = "Nome obrigatório")
	private String nome; 
	
	@Email(message = "Email deve ser válido")
	@NotBlank(message = "Email é obrigatório")
	private String email;
	
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