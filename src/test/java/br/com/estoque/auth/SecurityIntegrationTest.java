package br.com.estoque.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.estoque.dto.auth.AuthResponseDTO;
import br.com.estoque.dto.auth.LoginRequestDTO;
import br.com.estoque.dto.auth.RegisterRequestDTO;
import br.com.estoque.dto.auth.RegisterResponseDTO;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.usuario.Role;
import br.com.estoque.usuario.Usuario;
import br.com.estoque.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityIntegrationTest {

	@Autowired
	private AuthService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UsuarioRepository repository;
	
@Test
void validarGetSemToken() throws Exception {
	mockMvc.perform(get("/produtos"))
        .andExpect(status().isUnauthorized());
}

@Test
void validarGetUser()throws Exception{
	RegisterRequestDTO r = new RegisterRequestDTO(
			"emailTesteIntegration@emai.com",
			"TesteIntegration",
			"12345abc"	);
	
	service.registrar(r);
	
	LoginRequestDTO l = new LoginRequestDTO(
			r.getEmail(),
			r.getSenha());
	
	AuthResponseDTO a = service.login(l);
	
	String token = a.getToken();
	
	mockMvc.perform(get("/produtos")
	 .header("Authorization", "Bearer " + token))
	 .andExpect(status().isOk());
	
}

@Test
void validarPostUser()throws Exception{
	RegisterRequestDTO r = new RegisterRequestDTO(
			"emailTesteIntegration1@emai.com",
			"TesteIntegration1",
			"12345abc"	);
	
	service.registrar(r);
	
	LoginRequestDTO l = new LoginRequestDTO(
			r.getEmail(),
			r.getSenha());
	
	AuthResponseDTO a = service.login(l);

	String token = a.getToken();
	
	mockMvc.perform(post("/produtos")
	.header("Authorization", "Bearer " + token)
	.contentType(MediaType.APPLICATION_JSON)
    .content("""
        {
          "nome": "produtoTesteIntegration1",
          "quantidade": "1"
        }
    """))
	 .andExpect(status().isForbidden());
	
}

@Test
void validarPostAdmin()throws Exception{
	RegisterRequestDTO r = new RegisterRequestDTO(
			"emailTesteIntegration2@emai.com",
			"TesteIntegration2",
			"12345abc"	);
	
	RegisterResponseDTO s = service.registrar(r);
	
	Usuario b = repository.findByEmail(s.getEmail())
			.orElseThrow(() -> new ResourceNotFoundException ("Usuário não encontrado"));	
	b.setRole(Role.ADMIN);
	repository.save(b);
	

	LoginRequestDTO l = new LoginRequestDTO(
			r.getEmail(),
			r.getSenha());
	
	AuthResponseDTO a = service.login(l);
	
	String token = a.getToken();
	
	mockMvc.perform(post("/produtos")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
		    .content("""
		        {
		          "nome": "produtoTesteIntegration2",
		          "quantidade": "2"
		        }
		    """))
			 .andExpect(status().isCreated());
	
	
}











}
