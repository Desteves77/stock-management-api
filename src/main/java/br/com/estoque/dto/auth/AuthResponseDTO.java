package br.com.estoque.dto.auth;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta retornada após login bem-sucedido")
public class AuthResponseDTO {

    @Schema(description = "Token JWT gerado após autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
	String token;
	
    @Schema(description = "Tipo do token", example = "Bearer")
	String type;
	
	
public AuthResponseDTO() {
	
}
	
public AuthResponseDTO(String token, String type) {
	this.token= token;
	this.type = type;
}

public String getToken() {
	return token;
}

public String getType() {
	return type;
}

public void setToken(String token) {
	this.token= token;
}

public void setType(String type) {
	this.type = type;
}


	
	
	
}
