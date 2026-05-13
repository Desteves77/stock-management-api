package br.com.estoque.usuario;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

private UsuarioRepository repository;

public UsuarioDetailsService(UsuarioRepository repository){
	this.repository = repository;
}
	
@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	Usuario usuario = repository.findByEmail(email)
		.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	
	  return User.builder()
              .username(usuario.getEmail())
              .password(usuario.getSenha())
              .roles(usuario.getRole().name())
              .build();
  }
	
	
}
	
	

