package br.com.estoque.dto.auth;

import br.com.estoque.usuario.Role;

public class RegisterResponseDTO {

	private Long id;
	
	private String nome; 
	
	private String email;
	
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
