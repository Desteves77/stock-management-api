package br.com.estoque.usuario;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable= false)
	private String senha;
	
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