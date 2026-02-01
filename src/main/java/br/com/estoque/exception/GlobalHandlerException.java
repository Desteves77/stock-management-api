package br.com.estoque.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice
public class GlobalHandlerException {
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request){
		
	Map<String, String> fields= new HashMap<>();
	
	for(FieldError error: ex.getBindingResult().getFieldErrors()) {
		fields.put(error.getField(), error.getDefaultMessage());
	}
	
	ApiError body = new ApiError(
			LocalDateTime.now(),
			HttpStatus.BAD_REQUEST.value(),
			"Bad Request",
			"Erro de validação",
			request.getRequestURI(),
			fields);
		
	return ResponseEntity.badRequest().body(body);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request){
		
		ApiError body = new ApiError(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				ex.getMessage()			,
				request.getRequestURI());
			
		return ResponseEntity.badRequest().body(body);	
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request){
		
		
		ApiError body = new ApiError(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				"Not Found",
				ex.getMessage()			,
				request.getRequestURI());
			
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}
	
	 @ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request){
	
		ApiError body = new ApiError(
				LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error",
			    "Erro inesperado"		,
				request.getRequestURI());
			
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
		
		
}
	
	
	
	
	
	
}
