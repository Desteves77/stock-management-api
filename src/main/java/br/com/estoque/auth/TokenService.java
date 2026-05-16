package br.com.estoque.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.estoque.usuario.Usuario;

@Service
public class TokenService {

	private static final String ISSUER = "estoque-string-api";
	
	@Value("${jwt.secret}") 
	private String secret;
	
	@Value("${jwt.expiration-hours}")
	private Long expirationHours;
	
	
	public String  gerarToken (Usuario usuario){
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		return JWT.create()	
			.withIssuer(ISSUER)
			.withSubject(usuario.getEmail())
			.withClaim("role", usuario.getRole().name())
			.withExpiresAt(Instant.now().plus(expirationHours, ChronoUnit.HOURS))
			.sign(algorithm);
	}
	
	public String validarToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		return JWT.require(algorithm)
				.withIssuer(ISSUER)
				.build()
				.verify(token)
				.getSubject();
	}
	
} 
