package br.com.estoque.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import br.com.estoque.security.SecurityFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import br.com.estoque.dto.auth.AuthResponseDTO;
import br.com.estoque.dto.auth.LoginRequestDTO;
import br.com.estoque.dto.auth.RegisterRequestDTO;
import br.com.estoque.dto.auth.RegisterResponseDTO;
import br.com.estoque.exception.GlobalHandlerException;
import br.com.estoque.usuario.Role;


@WebMvcTest(
        controllers = AuthController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = SecurityFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalHandlerException.class)
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean 
	private AuthService service; 
	
	
@Test
void sucessoRegister() throws Exception {
	
	RegisterResponseDTO r = new RegisterResponseDTO();
	r.SetId(1L);
	r.setEmail("emailTesteController@email.com");
	r.setNome("testeController");
	r.setRole(Role.USER);
	
	when(service.registrar(any(RegisterRequestDTO.class))).thenReturn(r);
	
	mockMvc.perform(post("/auth/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("""
	                {
	                  "email": "emailTesteController@email.com",
	                  "nome": "testeController",
	                  "senha": "abc12345"
	                }
	            """))
	        .andExpect(status().isCreated())
	        .andExpect(jsonPath("$.id").value(1L))
	        .andExpect(jsonPath("$.nome").value("testeController"))
	        .andExpect(jsonPath("$.email").value("emailTesteController@email.com"))
	        .andExpect(jsonPath("$.role").value("USER"));
	}



@Test
void registerSenhaCurta() throws Exception {
	

	
	mockMvc.perform(post("/auth/register")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("""
	                {
	                  "email": "emailTesteController1@email.com",
	                  "nome": "testeController1",
	                  "senha": "123"
	                }
	            """))
	
	 		.andDo(print())
	        .andExpect(status().isBadRequest());
		
			verifyNoInteractions(service);
	}
	

@Test
void loginSucesso() throws Exception {
	
	AuthResponseDTO r = new AuthResponseDTO();
	r.setToken("JWT-SECRET");
	r.setType("Bearer");
	
	when(service.login(any(LoginRequestDTO.class))).thenReturn(r);
	
		mockMvc.perform(post("/auth/login")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("""
	                {
	                  "email": "emailTesteController2@email.com",
	                  "senha": "abc12345"
	                }
	            """))
	        .andExpect(status().isOk())
			.andExpect(jsonPath("$.token").value("JWT-SECRET"))
			.andExpect(jsonPath("$.type").value("Bearer"));
	}

@Test
void loginCrendencialErrada() throws Exception{
	
	when(service.login(any(LoginRequestDTO.class)))
		.thenThrow(new BadCredentialsException("Credenciais inválidas"));
	
	mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "email": "emailTesteController3@email.com",
                  "senha": "abc12345"
                }
            """))
        .andExpect(status().isUnauthorized());
}




	
}
