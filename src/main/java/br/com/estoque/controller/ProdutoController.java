package br.com.estoque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;


import java.util.List;
import java.util.Optional;

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
import br.com.estoque.entity.Produto;
import br.com.estoque.exception.ApiError;
import br.com.estoque.service.ProdutoService;
import jakarta.validation.Valid;
import br.com.estoque.dto.ProdutoCreateDTO;
import br.com.estoque.dto.ProdutoPatchDTO;
import br.com.estoque.dto.ProdutoPutDTO;

@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@RestController
@RequestMapping("/produtos")

public class ProdutoController {

private final ProdutoService service;
	
public ProdutoController(ProdutoService service) {
	this.service= service;
}
	

@Operation(
	    summary = "Cadastrar produto",
	    description = "Cria um novo produto a partir dos dados enviados no body.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	    		required = true,
	    		description = "Dados para cadastro do produto.",
	    		content = @Content(
	    		mediaType = "application/json",
	    		schema = @Schema(implementation = ProdutoCreateDTO.class),
	    		examples = {
	    		@ExampleObject(
	    			name = "Exemplo válido",
	    	        value = """
	    	        	{
	    	        	"nome": "Produto Teste",
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
	        description = "Produto criado com sucesso",
	        content = @Content(schema = @Schema(implementation = Produto.class))
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
public ResponseEntity<Produto> salvar(@Valid @RequestBody ProdutoCreateDTO dto) {
	
		 Produto produto = new Produto();
		 produto.setNome(dto.getNome());
		 produto.setQuantidade(dto.getQuantidade());
		 Produto salvo = service.salvar(produto);
		 
		 return ResponseEntity.status(201).body(salvo);
	 
}




@Operation(
	    summary = "Atualizar produto existente",
	    description = "Atualiza um produto a partir dos dados enviados no body",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	    	    required = true,
	    	    description = "Dados para atualizar produto existente.",
	    	    content = @Content(
	    	    mediaType = "application/json",
	    	    schema = @Schema(implementation = ProdutoPutDTO.class),
	    	    examples = {
	    	    @ExampleObject(
	    	    		name = "Atualizar nome e quantidade",
	    	    		value = """
	    	    			{
	    	    		    "nome": "Produto Teste2",
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
	        description = "Produto atualizado com sucesso",
	        content = @Content(schema = @Schema(implementation = Produto.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Dados inválidos (validação do DTO)",
	        content = @Content(schema = @Schema(implementation = ApiError.class))
	    ),
	    @ApiResponse(
		        responseCode = "404",
		        description = "Produto não encontrado (ID inexistente)",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    ),
	    @ApiResponse(
		        responseCode = "500",
		        description = "Erro inesperado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    )
	})
@PutMapping("/{id}")
public ResponseEntity<Produto> atualizar(@PathVariable @Parameter(description="ID do produto", example="1", required=true) Long id, @Valid @RequestBody ProdutoPutDTO Putdto) {

		Produto produto = new Produto();
		produto.setNome(Putdto.getNome());
		produto.setQuantidade(Putdto.getQuantidade());
		Produto novoProduto = service.atualizar(id, produto);
		
		return ResponseEntity.ok(novoProduto);
	
}


@Operation(
	    summary = "Atualizar parcialmente produto existente",
	    description = "Atualiza um produto existente a partir dos dados enviados no body.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	    	required = true,
	    	description = "Dados para atualizar parcialmente produto existente.",
	    	content = @Content(
	    	mediaType = "application/json",
	    	schema = @Schema(implementation = ProdutoPatchDTO.class),
	    	examples = {
	    		@ExampleObject(
	    				name = "Atualizar só nome",
	    				value = """
	    				{
	    				 "nome": "Produto Teste2"
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
	        description = "Produto atualizado com sucesso",
	        content = @Content(schema = @Schema(implementation = Produto.class))
	    ),
	    @ApiResponse(
	        responseCode = "400",
	        description = "Dados inválidos (validação do DTO)",
	        content = @Content(schema = @Schema(implementation = ApiError.class))
	    ),
	    @ApiResponse(
		        responseCode = "404",
		        description = "Produto não encontrado (ID inexistente)",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    ),
	    @ApiResponse(
		        responseCode = "500",
		        description = "Erro inesperado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    )
	})
@PatchMapping("/{id}")
public ResponseEntity<Produto> atualizarParcial(@PathVariable @Parameter(description="ID do produto", example="1", required=true) Long id, @Valid @RequestBody ProdutoPatchDTO parcial){
	
		Produto produto = new Produto();
		produto.setNome(parcial.getNome());
		produto.setQuantidade(parcial.getQuantidade());
		Produto salvoParcial = service.atualizarParcial(id, produto);
		return ResponseEntity.ok(salvoParcial);
	
}

@Operation(
	    summary = "Listar produtos",
	    description = "Lista produtos existentes."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Produtos listados com sucesso",
	        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Produto.class)))
	    ),
	    @ApiResponse(
		        responseCode = "500",
		        description = "Erro inesperado",
		        content = @Content(schema = @Schema(implementation = ApiError.class))
		    )
	})
@GetMapping
public ResponseEntity<List<Produto>> listar(){
	List<Produto> listar =  service.listarTodos();
	return ResponseEntity.ok(listar);
}

@Operation(
	    summary = "Listar produto",
	    description = "Lista produto existente."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "200",
	        description = "Produto listado com sucesso",
	        content = @Content(schema = @Schema(implementation = Produto.class))
	        
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
public ResponseEntity<Produto> buscarporId(@PathVariable @Parameter(description="ID do produto", example="1", required=true) Long id) {
	Optional<Produto> buscar = service.buscaporId(id);
	
	return ResponseEntity.ok(buscar.get());
}


@Operation(
	    summary = "Deletar produto",
	    description = "Deleta produto existente."
	)
	@ApiResponses({
	    @ApiResponse(
	        responseCode = "204",
	        description = "Produto deletado com sucesso"
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
public ResponseEntity<Void> remover(@PathVariable @Parameter(description="ID do produto", example="1", required=true) Long id) {
	boolean produto = service.remover(id);
	if(!produto) {
		return ResponseEntity.notFound().build();
	}
	
	return ResponseEntity.noContent().build();
}














	
}
