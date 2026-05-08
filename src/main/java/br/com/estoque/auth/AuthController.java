package br.com.estoque.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.estoque.dto.auth.RegisterRequestDTO;
import br.com.estoque.dto.auth.RegisterResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/auth")
public class AuthController {

	
private final AuthService service;

public AuthController(AuthService service) {
	this.service = service;
}
	
	
	
@PostMapping("/register")
public ResponseEntity<RegisterResponseDTO> registrar (@Valid @RequestBody RegisterRequestDTO dto){ 
	
	RegisterResponseDTO response = service.registrar(dto);
	
	return ResponseEntity.status(201).body(response);
	
	
}
	
	
}
