package br.com.estoque.usuario;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Entidade que representa um usuário do sistema")
@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Schema(description = "Identificador único do usuário", example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Schema(description = "Nome do usuário", example = "Davi Esteves")
	@Column(nullable = false)
	private String nome;
	
	@Schema(description = "Email único usado para login", example = "davi@email.com")
	@Column(unique = true, nullable = false)
	private String email;
	
	@Schema(description = "Senha armazenada com hash BCrypt", accessMode = Schema.AccessMode.WRITE_ONLY)
	@Column(nullable= false)
	private String senha;
	
	@Schema(description = "Perfil de acesso do usuário", example = "USER")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	public Usuario() {
		
	}
	
	public Usuario(String nome, String email, String senha, Role role ) {
		this.nome = nome;
		this.email= email;
		this.senha= senha;
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

public String getSenha() {
	return senha;
}

public Role getRole() {
	return role;
}

public void setId (Long id) {
	this.id = id;
}

public void setNome(String nome) {
	this.nome = nome;
}

public void setEmail(String email) {
	this.email= email;
}

public void setSenha(String senha) {
	this.senha= senha;
}

public void setRole(Role role) {
	this.role = role;
}


	
	
	
}