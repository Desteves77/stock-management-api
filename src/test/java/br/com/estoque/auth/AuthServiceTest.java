package br.com.estoque.auth;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import br.com.estoque.dto.auth.AuthResponseDTO;
import br.com.estoque.dto.auth.LoginRequestDTO;
import br.com.estoque.dto.auth.RegisterRequestDTO;
import br.com.estoque.dto.auth.RegisterResponseDTO;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.usuario.Role;
import br.com.estoque.usuario.Usuario;
import br.com.estoque.usuario.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@Mock
	private UsuarioRepository repository;
	
	@Mock
	private PasswordEncoder encoder;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private TokenService tokenService;
	
	@InjectMocks
	private AuthService service;
	
	
	
@Test
void SucessoRegister() {
    RegisterRequestDTO request = new RegisterRequestDTO();
    request.setNome("teste");
    request.setEmail("emailteste@email.com");
    request.setSenha("12345678");

    when(repository.existsByEmail(request.getEmail())).thenReturn(false);
    when(encoder.encode(request.getSenha())).thenReturn("senhaComHash");

    when(repository.save(any(Usuario.class))).thenAnswer(invocation -> {
        Usuario usuario = invocation.getArgument(0);
        usuario.setId(1L);
        return usuario;
    });

    RegisterResponseDTO response = service.registrar(request);

    ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
    verify(repository).save(captor.capture());

    Usuario usuarioSalvo = captor.getValue();

    assertEquals(request.getNome(), usuarioSalvo.getNome());
    assertEquals(request.getEmail(), usuarioSalvo.getEmail());
    assertEquals("senhaComHash", usuarioSalvo.getSenha());
    assertEquals(Role.USER, usuarioSalvo.getRole());

    assertEquals(1L, response.getId());
    assertEquals(request.getNome(), response.getNome());
    assertEquals(request.getEmail(), response.getEmail());
    assertEquals(Role.USER, response.getRole());

    verify(repository).existsByEmail(request.getEmail());
    verify(encoder).encode(request.getSenha());
    verify(repository).save(any(Usuario.class));
}


@Test
void RegisterNomeVazio() {
	RegisterRequestDTO r = new RegisterRequestDTO();
	r.setEmail("emailteste2@email.com");
	r.setNome("");
	r.setSenha("12345678");
	
	assertThrows(IllegalArgumentException.class, () ->{
		service.registrar(r);	
	});	
	
	verifyNoInteractions(repository);
	verifyNoInteractions(encoder);
	
}

@Test
void RegisterEmailVazio() {
	RegisterRequestDTO r = new RegisterRequestDTO();
	r.setEmail("");
	r.setNome("teste2");
	r.setSenha("12345678");
	
	assertThrows(IllegalArgumentException.class, () ->{
		service.registrar(r);	
	});	
	
	verifyNoInteractions(repository);
	verifyNoInteractions(encoder);
	
}

@Test
void RegisterSenhaVazia() {
	RegisterRequestDTO r = new RegisterRequestDTO();
	r.setEmail("emailteste4@email.com");
	r.setNome("test4");
	r.setSenha("");
	
	assertThrows(IllegalArgumentException.class, () ->{
		service.registrar(r);	
	});	
	
	verifyNoInteractions(repository);
	verifyNoInteractions(encoder);
	
}

@Test
void RegisterSenhaPequena() {
	RegisterRequestDTO r = new RegisterRequestDTO();
	r.setEmail("emailteste5@email.com");
	r.setNome("teste6");
	r.setSenha("abc");

	assertThrows(IllegalArgumentException.class, () ->{
		service.registrar(r);	
	});	
	
	verifyNoInteractions(repository);
	verifyNoInteractions(encoder);
	
}
	
@Test
void LoginGeraToken() {
	LoginRequestDTO l = new LoginRequestDTO();
	l.setEmail("emailteste6@email.com");
	l.setSenha("12345678");
	
	Usuario u = new Usuario();
	u.setId(1L);
	u.setEmail(l.getEmail());
	u.setNome("teste6");
	u.setSenha(l.getSenha());
	u.setRole(Role.USER);
	
	
	 when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
     .thenReturn(new UsernamePasswordAuthenticationToken(l.getEmail(), l.getSenha()));
	 
	 when(repository.existsByEmail(l.getEmail())).thenReturn(true);
	 
	 
	 when(repository.findByEmail(l.getEmail())).thenReturn(Optional.of(u));
	 
	 when(tokenService.gerarToken(u)).thenReturn("JWT-Secret");
	 
	 
	 AuthResponseDTO a = service.login(l);
	 
	 assertEquals("JWT-Secret", a.getToken());
	 assertEquals("Bearer", a.getType());
	 
	 verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
	 verify(repository).findByEmail(l.getEmail());
	 verify(tokenService).gerarToken(u);
	
}	

@Test
void LoginEmailVazio() {
	LoginRequestDTO l = new LoginRequestDTO();
	l.setEmail("");
	l.setSenha("12345678");
	
	assertThrows(IllegalArgumentException.class, () ->{
		service.login(l);
	});
	
	verifyNoInteractions(authenticationManager);
	verifyNoInteractions(repository);
	verifyNoInteractions(tokenService);
		
}

@Test
void LoginValidarEmail() {
	LoginRequestDTO l = new LoginRequestDTO();
	l.setEmail("emailteste7@email.com");
	l.setSenha("12345678");
	
	when(repository.existsByEmail(l.getEmail())).thenReturn(false);
	
	assertThrows(ResourceNotFoundException.class, () ->{
		service.login(l);
	});
		
}

@Test
void LoginSenhaVazia() {
	LoginRequestDTO l = new LoginRequestDTO();
	l.setEmail("emailteste7@email.com");
	l.setSenha("");
	
	
	
	when(repository.existsByEmail(l.getEmail())).thenReturn(true);
	
	assertThrows(IllegalArgumentException.class, () ->{
		service.login(l);
	});
		
}

@Test
void RegisterEmailExistente() {
	RegisterRequestDTO r = new RegisterRequestDTO();
	r.setEmail("emailteste2@email.com");
	r.setNome("teste3");
	r.setSenha("12345678");
	
	when(repository.existsByEmail(r.getEmail())).thenReturn(true);
	
	assertThrows(IllegalArgumentException.class, () ->{
		service.registrar(r);	
	});	
	
}












}

















