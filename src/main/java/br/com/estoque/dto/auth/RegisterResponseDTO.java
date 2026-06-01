package br.com.estoque.dto.auth;

import br.com.estoque.usuario.Role;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta retornada após registro de usuário")
public class RegisterResponseDTO {

    @Schema(description = "ID do usuário criado", example = "1")
	private Long id;
	
    @Schema(description = "Nome do usuário", example = "Davi Esteves")
	private String nome; 
	
    @Schema(description = "Email do usuário", example = "davi@email.com")
	private String email;
	
    @Schema(description = "Perfil de acesso do usuário", example = "USER")
	private Role role; 
	

public RegisterResponseDTO() {
	
}

public RegisterResponseDTO(Long id, String nome, String email, Role role){
	this.id = id;
	this.nome = nome;
	this.email = email;
	this.role = role;
}

public Long getId() {
	return id;
}

public String getNome() {
	return nome;
}

public String getEmail() {
	return email;
}

public Role getRole() {
	return role;
}

public void SetId(Long id) {
	this.id = id;
}

public void setNome(String nome) {
	this.nome= nome; 
}

public void setEmail(String email) {
	this.email= email;
}

public void setRole(Role role) {
	this.role = role;
}







}
