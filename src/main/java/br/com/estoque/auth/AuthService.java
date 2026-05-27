package br.com.estoque.auth;

import br.com.estoque.usuario.UsuarioRepository;
import br.com.estoque.dto.auth.AuthResponseDTO;
import br.com.estoque.dto.auth.LoginRequestDTO;
import br.com.estoque.dto.auth.RegisterRequestDTO;
import br.com.estoque.dto.auth.RegisterResponseDTO;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.usuario.Role;
import br.com.estoque.usuario.Usuario;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
private final UsuarioRepository repository;

private final PasswordEncoder passwordEncoder;

private final AuthenticationManager authenticationManager;

private final TokenService tokenService;

public AuthService(UsuarioRepository repository, PasswordEncoder passwordEncoder, 
		AuthenticationManager authenticationManager, TokenService tokenService) {
	this.repository = repository;
	this.passwordEncoder = passwordEncoder;
	this.authenticationManager= authenticationManager;
	this.tokenService= tokenService;
}

public RegisterResponseDTO registrar (RegisterRequestDTO registrar) {
	
	if(registrar.getNome() == null|| registrar.getNome().isBlank()) {
		throw new IllegalArgumentException("Nome não pode ser vazio");
	}

	if(registrar.getEmail()== null|| registrar.getEmail().isBlank()){
		throw new IllegalArgumentException("Email não pode ser vazio");
	}
	
	if(registrar.getSenha() == null|| registrar.getSenha().isBlank()){
		throw new IllegalArgumentException("Senha não pode ser vazia");
	}
	
	if(registrar.getSenha().length()<8) {
		throw new IllegalArgumentException("Senha não pode ser menor que 8 caracteres");
	}
	
	if(repository.existsByEmail(registrar.getEmail())) {
		throw new IllegalArgumentException("Esse email já está cadastrado");
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

public AuthResponseDTO login (LoginRequestDTO Login) {
	
	if(Login.getEmail()== null|| Login.getEmail().isBlank()){
		throw new IllegalArgumentException("Email não pode ser vazio");
	}
	
	if(!repository.existsByEmail(Login.getEmail())) {
		throw new ResourceNotFoundException("Credencias inválidas");
	}
	
	if(Login.getSenha() == null|| Login.getSenha().isBlank()){
		throw new IllegalArgumentException("Senha não pode ser vazia");
	}
	
	UsernamePasswordAuthenticationToken authToken = 
			new UsernamePasswordAuthenticationToken(
					Login.getEmail(), 
					Login.getSenha());
	
	authenticationManager.authenticate(authToken);
	
	Usuario usuario = repository.findByEmail(Login.getEmail())
			.orElseThrow(() -> new ResourceNotFoundException ("Usuário não encontrado"));
	
	String token = tokenService.gerarToken(usuario);
	
	return new AuthResponseDTO(token, "Bearer");
	
}

}
