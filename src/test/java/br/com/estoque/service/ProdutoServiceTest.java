package br.com.estoque.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.estoque.entity.Produto;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

	@Mock
	private ProdutoRepository produtoRepository;
	
	@InjectMocks
	private ProdutoService produtoService;
	
	
@Test
void validarNome() {
	Produto produto = new Produto();
	produto.setNome("");
	produto.setQuantidade(10);
	
	
	 assertThrows(IllegalArgumentException.class, () -> {
         produtoService.salvar(produto);
     });
	
	 verifyNoInteractions(produtoRepository);
}
	

@Test
void validarQuantidade() {	
	Produto produto = new Produto();
	produto.setNome("a");
	produto.setQuantidade(-1);
	
	
	assertThrows(IllegalArgumentException.class, () -> {
		produtoService.salvar(produto);
	});
	
	
	verifyNoInteractions(produtoRepository);;
}

@Test
void validarIdInexistente() {
	Long id= 999L;
	
	when(produtoRepository.existsById(id)).thenReturn(false);
	
	assertThrows(ResourceNotFoundException.class, () -> {
		produtoService.buscaporId(id);
	});
}

@Test
void validarIdExistente() {
	Produto produto = new Produto();
	produto.setId(1L);
	produto.setNome("A");
	produto.setQuantidade(1);
	
	when(produtoRepository.existsById(1L)).thenReturn(true);
	when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
	
	Optional<Produto> resultado = produtoService.buscaporId(1L);
	
	assertTrue(resultado.isPresent());
	assertEquals(1L, resultado.get().getId());
	assertEquals("A", resultado.get().getNome());
	assertEquals(1, resultado.get().getQuantidade());
	
	
}

void validarIdNulo() {
	
	assertThrows(IllegalArgumentException.class, () ->{
		produtoService.buscaporId(null);
	});
}


@Test
void validarProduto(){
	
	Produto produto = new Produto();
	produto.setId(1L);
	produto.setNome("A");
	produto.setQuantidade(1);
	
	when(produtoRepository.save(produto)).thenReturn(produto);
	
	Produto resultado = produtoService.salvar(produto);
	
	assertNotNull(resultado);
	assertEquals(1, resultado.getId());
	assertEquals("A", resultado.getNome());
	assertEquals(1, resultado.getQuantidade());
	
}
	
}
