package br.com.estoque.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import br.com.estoque.entity.Produto;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.repository.ProdutoRepository;
import br.com.estoque.service.ProdutoService;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class ProdutoIntegrationTest {
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	ProdutoRepository produtoRepository;
	

	
@Test
void validarSalvar() {
	Produto p = new Produto();
	p.setNome("Teste");
	p.setQuantidade(1);
	
	Produto salvo = produtoService.salvar(p);
	
    assertNotNull(salvo.getId());	
	
	
}

@Test
void validarNegativo() {
	Produto p = new Produto();
	p.setNome("Teste");
	p.setQuantidade(-1);
	p.setId(1L);
	
	assertThrows(IllegalArgumentException.class, () ->{
		produtoService.salvar(p);
	});
}
	

@Test
void validarAtualizar() {
	Produto p = new Produto();
	p.setNome("Teste");
	p.setQuantidade(1);

	
	Produto salvo = produtoService.salvar(p);
	
	Produto a = new Produto();
	a.setNome("Teste 2");
	a.setQuantidade(2);
	
	Produto atualizado = produtoService.atualizar(salvo.getId(), a);
	
	
    assertEquals("Teste 2", atualizado.getNome());	
    assertEquals(2, atualizado.getQuantidade());
    assertEquals(salvo.getId(), atualizado.getId());
	
	
}

@Test
void validarNomeAtualizar() {
	Produto p = new Produto();
	p.setNome("Teste");
	p.setQuantidade(2);
	
	Produto salvo = produtoService.salvar(p);
	
	Produto a = new Produto();
	a.setNome("");
	a.setQuantidade(3);
	
	Produto banco = produtoRepository.findById(salvo.getId()).get();
	
	
	assertThrows(IllegalArgumentException.class, () ->{
		produtoService.atualizar(salvo.getId(), a);
	});
	
	assertEquals("Teste", banco.getNome());
	assertEquals(2, banco.getQuantidade());
	
}

@Test
void validarAtualizarParcial() {
	Produto p= new Produto();
	p.setNome("Teste");
	p.setQuantidade(1);
	
	Produto salvo = produtoService.salvar(p);
	
	Produto nome = new Produto();
	nome.setNome("Teste2");
	
	Produto parcial = produtoService.atualizarParcial(salvo.getId(), nome);
	
	assertEquals(parcial.getNome(), "Teste2");
	assertEquals(parcial.getQuantidade(), 1);
	assertEquals(parcial.getId(), salvo.getId());
	
	Produto banco = produtoRepository.findById(salvo.getId()).get();
	
	assertEquals("Teste2", banco.getNome());
	assertEquals(1, banco.getQuantidade());
	assertEquals(salvo.getId(), banco.getId());
		
}

@Test
void validarQuantidadeParcial() {
	Produto p = new Produto();
	p.setNome("Teste");
	p.setQuantidade(2);
	
	Produto salvo = produtoService.salvar(p);
	
	Produto q = new Produto();
	q.setNome("Teste");
	q.setQuantidade(-10);
	
	Produto banco = produtoRepository.findById(salvo.getId()).get();
	
	assertThrows(IllegalArgumentException.class, () -> {
		produtoService.atualizarParcial(salvo.getId(), q );
	});
	
	assertEquals("Teste", banco.getNome());
	assertEquals(2, banco.getQuantidade());
	assertEquals(salvo.getId(), banco.getId());	
}

@Test
void validarBuscarPorId() {
	Produto p = new Produto();
	p.setNome("Teste");
	p.setQuantidade(2);
	
	Produto s = produtoService.salvar(p);
	
	Produto b = produtoService.buscaporId(s.getId()).get();
	
	assertEquals("Teste", b.getNome());
	assertEquals(2, b.getQuantidade());
	assertEquals(s.getId(), b.getId());
}

@Test
void validarIdInvalido() {
	Long id = 333L;
	
	assertThrows(ResourceNotFoundException.class, () ->{
		produtoService.buscaporId(id);
	});
	

}

@Test
void validarDelete() {
	Produto p = new Produto();
	p.setNome("Teste");
	p.setQuantidade(1);
	
	Produto s = produtoService.salvar(p);
	
	Boolean del = produtoService.remover(p.getId());
	
	Boolean v = produtoRepository.existsById(s.getId());
	
	 assertTrue(del);
	 assertFalse(v);
	
}

@Test
void validarIdDelete() {
	Long id = 999L;
	
	assertThrows(ResourceNotFoundException.class, () ->{
		produtoService.remover(id);
	});
	
	
}

@Test
void validarPageable() {
	
	
	Pageable pageable = PageRequest.of(0, 2);
	
	Page<Produto> resultado = produtoService.listarTodos(pageable);
	
	assertEquals(produtoRepository.count(), resultado.getTotalElements());

}


@Test
void validarPageable100() {
	
	Pageable pageable = PageRequest.of(0 , 101);
	assertThrows(IllegalArgumentException.class, () -> {
		produtoService.listarTodos(pageable);
	});


}


}
