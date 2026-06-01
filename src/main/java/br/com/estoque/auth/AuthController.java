package br.com.estoque.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.estoque.dto.auth.RegisterRequestDTO;
import br.com.estoque.dto.auth.RegisterResponseDTO;
import br.com.estoque.dto.auth.LoginRequestDTO;
import br.com.estoque.dto.auth.AuthResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Endpoints de registro e login de usuários")
@RestController 
@RequestMapping("/auth")
public class AuthController {

	
private final AuthService service;

public AuthController(AuthService service) {
	this.service = service;
}
			


@Operation(
        summary = "Registrar usuário",
        description = "Cria uma nova conta de usuário com role padrão USER."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já cadastrado")
})

@PostMapping("/register")
public ResponseEntity<RegisterResponseDTO> registrar (@Valid @RequestBody RegisterRequestDTO dto){ 
	
	RegisterResponseDTO response = service.registrar(dto);
	
	return ResponseEntity.status(201).body(response);
	
	
}

@Operation(
        summary = "Realizar login",
        description = "Autentica o usuário e retorna um token JWT do tipo Bearer."
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Email ou senha vazios"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "404", description = "Email não cadastrado")
})

@PostMapping("/login")
public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto){
	
	AuthResponseDTO response = service.login(dto);
	
	return ResponseEntity.status(200).body(response);
}


	
}
