package br.com.estoque.controller;


import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.estoque.dto.InsumoCreateDTO;
import br.com.estoque.dto.InsumoPatchDTO;
import br.com.estoque.dto.InsumoPutDTO;
import br.com.estoque.entity.Insumo;
import br.com.estoque.exception.ApiError;
import br.com.estoque.service.InsumoService;
import jakarta.validation.Valid;


@Tag(name= "Insumos", description= "Endpoints para gerenciamento de Insumos")
@RestController
@RequestMapping("/insumos")

class InsumoController {
	
private final InsumoService service;

public InsumoController(InsumoService service) {
	this.service= service;
}


@Operation(
		summary = "Cadastrar insumo",
	    description = "Cria um novo insumo a partir dos dados enviados no body.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	    		required = true,
	    		description = "Dados para cadastro do insumo.",
	    		content = @Content(
	    		mediaType = "application/json",
	    		schema = @Schema(implementation = InsumoCreateDTO.class),
	    		examples = {
	    		@ExampleObject(
	    			name = "Exemplo válido",
	    	        value = """
	    	        	{
	    	        	"nome": "Insumo Teste",
	    	        	"quantidade": 10
	    	        	}
	    	            """
        )
      }
    )
  )    		
)

@ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Insumo criado com sucesso",
        content = @Content(schema = @Schema(implementation = Insumo.class))
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Dados inválidos (validação do DTO)",
        content = @Content(schema = @Schema(implementation = ApiError.class))
    ),
    @ApiResponse(
	        responseCode = "500",
	        description = "Erro inesperado",
	        content = @Content(schema = @Schema(implementation = ApiError.class))
	    )
})

@PostMapping 
public ResponseEntity<Insumo> salvar(@RequestBody @Valid InsumoCreateDTO insumoDTO) {
		Insumo insumo = new Insumo();
		insumo.setNome(insumoDTO.getNome());
		insumo.setQuantidade(insumoDTO.getQuantidade());
		Insumo salvo = service.salvar(insumo);
		
		return ResponseEntity.status(201).body(salvo);
	
}

@Operation(
	    summary = "Atualizar insumo existente",
	    description = "Atualiza um insumo a partir dos dados enviados no body",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	    	    required = true,
	    	    description = "Dados para atualizar insumo existente.",
	    	    content = @Content(
	    	    mediaType = "application/json",
	    	    schema = @Schema(implementation = InsumoPutDTO.class),
	    	    examples = {
	    	    @ExampleObject(
	    	    		name = "Atualizar nome e quantidade",
	    	    		value = """
	    	    			{
	    	    		    "nome": "Insumo Teste2",
	    	    		    "quantidade": 11
	    	    		    }
	    	    		"""
	    )
	  }
    )
  )    			
)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Insumo atualizado com sucesso",
	        content = @Content(schema = @Schema(implementation = Insumo.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Dados inválidos (validação do DTO)",
	        content = @Content(schema = @Schema(implementation = ApiError.class))
	    ),
	    @ApiResponse(
		        responseCode = "404",
		        description = "Insumo não encontrado (ID inexistente)",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    ),
	    @ApiResponse(
		        responseCode = "500",
		        description = "Erro inesperado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    )
	})


@PutMapping("/{id}")
public ResponseEntity<Insumo> atualizar(@PathVariable @Parameter(description="ID do insumo", example="1", required=true) Long id, @Valid @RequestBody InsumoPutDTO novoInsumo) {
		Insumo insumo= new Insumo();
		insumo.setNome(novoInsumo.getNome());
		insumo.setQuantidade(novoInsumo.getQuantidade());
		Insumo salvo = service.atualizar(id, insumo);
		
		return ResponseEntity.ok(salvo);
	
	
}

@Operation(
	    summary = "Atualizar parcialmente insumo existente",
	    description = "Atualiza um insumo existente a partir dos dados enviados no body.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	    	required = true,
	    	description = "Dados para atualizar parcialmente insumo existente.",
	    	content = @Content(
	    	mediaType = "application/json",
	    	schema = @Schema(implementation = InsumoPatchDTO.class),
	    	examples = {
	    		@ExampleObject(
	    				name = "Atualizar só nome",
	    				value = """
	    				{
	    				 "nome": "Insumo Teste2"
	    				}
	    				   """
	    				),
	    		@ExampleObject(
	    				name = "Atualizar só quantiade",
	    	    		value = """
	    	    		{
	    	    		 "quantidade": 12
	    	    		}
	    	    	    """    		
	    )		
      }
	)
  )
)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Insumo atualizado com sucesso",
	        content = @Content(schema = @Schema(implementation = Insumo.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Dados inválidos (validação do DTO)",
	        content = @Content(schema = @Schema(implementation = ApiError.class))
	    ),
	    @ApiResponse(
		        responseCode = "404",
		        description = "Insumo não encontrado (ID inexistente)",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    ),
	    @ApiResponse(
		        responseCode = "500",
		        description = "Erro inesperado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    )
	})


@PatchMapping("/{id}")
public ResponseEntity<Insumo> atualizarParcial(@PathVariable @Parameter(description="ID do insumo", example="1", required=true) Long id, @Valid @RequestBody InsumoPatchDTO parcial){
		Insumo insumo = new Insumo();
		insumo.setNome(parcial.getNome());
		insumo.setQuantidade(parcial.getQuantidade());
		Insumo salvo = service.atualizarParcial(id, insumo);
		
		return ResponseEntity.ok(salvo);
	
}

@Operation(
	    summary = "Listar insumos",
	    description = "Lista insumos existentes."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Insumos listados com sucesso",
	        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Insumo.class)))
	    ),
	    @ApiResponse(
		        responseCode = "500",
		        description = "Erro inesperado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    )
	})
@GetMapping
public ResponseEntity<Page<Insumo>> listarTodos(@ParameterObject Pageable pageable){
	Page<Insumo> listar=  service.listarTodos(pageable);
	return ResponseEntity.ok(listar);
}

@Operation(
	    summary = "Listar insumo",
	    description = "Lista insumo existente."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Insumo listado com sucesso",
	        content = @Content(schema = @Schema(implementation = Insumo.class))
	        
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "ID inválido",
	        content = @Content(schema = @Schema(implementation = ApiError.class))
	        
	    ),
	    @ApiResponse(
		        responseCode = "404",
		        description = "ID não encontrado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    ),
	    @ApiResponse(
		        responseCode = "500",
		        description = "Erro inesperado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    )
	    
	    
	})

@GetMapping("/{id}")
public ResponseEntity<Insumo> buscarporId(@PathVariable @Parameter(description="ID do insumo", example="1", required=true) Long id) {
	Optional<Insumo> buscar = service.buscaporId(id);

	return ResponseEntity.ok(buscar.get());
}

@Operation(
	    summary = "Deletar insumo",
	    description = "Deleta insumo existente."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "204",
	        description = "Insumo deletado com sucesso"
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "ID inválido",
	        content = @Content(schema = @Schema(implementation = ApiError.class))
	    ),
	    @ApiResponse(
		        responseCode = "404",
		        description = "ID não encontrado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    ),
	    @ApiResponse(
		        responseCode = "500",
		        description = "Erro inesperado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    )
	    
	    
	})

@DeleteMapping("/{id}")
public ResponseEntity<Void> remover(@PathVariable @Parameter(description="ID do insumo", example="1", required=true) Long id) {
	boolean remover = service.remover(id);
		if(!remover) {
			return ResponseEntity.notFound().build();
		}
	return ResponseEntity.noContent().build();
}
	








}
