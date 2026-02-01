package br.com.estoque.exception;


import java.time.LocalDateTime;
import java.util.Map;
import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonInclude;

@Schema(description = "Formato padrão de erro retornado pela API")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

	@Schema(description = "Data/hora do erro", example = "2026-01-21T20:58:06.5002759")
	private LocalDateTime timestamp;
	@Schema(description = "Código HTTP", example = "400")
	private int status;
	@Schema(description = "Nome do erro HTTP", example = "Bad Request/NotFound/Internal Server Error")
	private String error;
	@Schema(description = "Mensagem detalhada do erro", example = "Dados inválidos (validação do DTO)")
	private String message;
	@Schema(description = "Path da requisição", example = "/produtos/1")
	private String path;
	@Schema(description = "Erros de validação por campo (campo -> mensagem). Pode vir ausente quando não for validação.")
	private Map<String, String> fields;
	
	
public ApiError(LocalDateTime timestamp, int status, String error, String message, String path){	
	this.timestamp= timestamp;
	this.status= status;
	this.error= error;
	this.message= message;
	this.path= path; 
	
}

public ApiError(LocalDateTime timestamp, int status, String error, String message, String path, Map<String, String> fields) {
	this(timestamp, status, error, message, path);
	this.fields= fields;
}


public LocalDateTime getTimestamp() {return timestamp;}
public int getStatus() {return status;}
public String getError() {return error;}
public String getMessage() {return message;}
public String getPath() {return path;}

public Map<String, String> getFields(){return fields;}









}
