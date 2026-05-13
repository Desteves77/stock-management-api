package br.com.estoque.auth;

import br.com.estoque.usuario.UsuarioRepository;
//import br.com.estoque.dto.auth.LoginRequestDTO;
import br.com.estoque.dto.auth.RegisterRequestDTO;
import br.com.estoque.dto.auth.RegisterResponseDTO;
import br.com.estoque.usuario.Role;
import br.com.estoque.usuario.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	
private final UsuarioRepository repository;

private final PasswordEncoder passwordEncoder;
	

public AuthService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
	this.repository = repository;
	this.passwordEncoder = passwordEncoder;
}


public RegisterResponseDTO registrar (RegisterRequestDTO registrar) {
	
	if(registrar.getNome() == null|| registrar.getNome().isBlank()) {
		throw new IllegalArgumentException("Nome não pode ser vazio");
	}

	if(registrar.getEmail()== null|| registrar.getEmail().isBlank()){
		throw new IllegalArgumentException("Email não pode ser vazio");
	}
	
	if(repository.existsByEmail(registrar.getEmail())) {
		throw new IllegalArgumentException("Esse email já está cadastrado");
	}
	
	
	if(registrar.getSenha() == null|| registrar.getSenha().isBlank()){
		throw new IllegalArgumentException("Senha não pode ser vazia");
	}
	
	if(registrar.getSenha().length()<8) {
		throw new IllegalArgumentException("Senha não pode ser menor que 8 caracteres");
	}
	
	String hash = passwordEncoder.encode(registrar.getSenha());
	
	Usuario usuario = new Usuario();
	usuario.setNome(registrar.getNome());
	usuario.setEmail(registrar.getEmail());
	usuario.setRole(Role.USER); 
	usuario.setSenha(hash);
	
	Usuario salvo = repository.save(usuario);
	
	return new RegisterResponseDTO(
					(salvo.getId()),
					(salvo.getNome()),
					(salvo.getEmail()),
					(salvo.getRole())
									);
	
	
}

//public AuthResponseDTO login (LoginRequestDTO Login) {
	
//	if(Login.getEmail()== null|| Login.getEmail().isBlank()){
//		throw new IllegalArgumentException("Email não pode ser vazio");
//	}
	
//	if(Login.getSenha() == null|| Login.getSenha().isBlank()){
//		throw new IllegalArgumentException("Senha não pode ser vazia");
//	}
	
	
	
//}


	
}
