package br.com.estoque.dto.auth;

public class AuthResponseDTO {

	String token;
	
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
