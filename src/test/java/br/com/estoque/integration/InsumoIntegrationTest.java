package br.com.estoque.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import br.com.estoque.entity.Insumo;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.repository.InsumoRepository;
import br.com.estoque.service.InsumoService;


import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class InsumoIntegrationTest {

	@Autowired
	InsumoService insumoService;
	
	@Autowired
	InsumoRepository insumoRepository;
	

	
@Test
void validarSalvar() {
	Insumo i = new Insumo();
	i.setNome("Teste");
	i.setQuantidade(1);
	
	Insumo salvo = insumoService.salvar(i);
	
    assertNotNull(salvo.getId());	
	
	
}

@Test
void validarNegativo() {
	Insumo i = new Insumo();
	i.setNome("Teste");
	i.setQuantidade(-1);
	
	assertThrows(IllegalArgumentException.class, () ->{
		insumoService.salvar(i);
	});
}
	

@Test
void validarAtualizar() {
	Insumo i = new Insumo();
	i.setNome("Teste");
	i.setQuantidade(1);
	
	Insumo salvo = insumoService.salvar(i);
	
	Insumo a = new Insumo();
	a.setNome("Teste 33");
	a.setQuantidade(2);
	
	Insumo atualizado = insumoService.atualizar(salvo.getId(), a);
	
	
    assertEquals("Teste 33", atualizado.getNome());	
    assertEquals(2, atualizado.getQuantidade());
    assertEquals(salvo.getId(), atualizado.getId());
	
	
}

@Test
void validarNomeAtualizar() {
	Insumo i = new Insumo();
	i.setNome("Teste55");
	i.setQuantidade(2);
	
	Insumo salvo = insumoService.salvar(i);
	
	Insumo a = new Insumo();
	a.setNome("");
	a.setQuantidade(3);
	
	Insumo banco = insumoRepository.findById(salvo.getId()).get();
	
	
	assertThrows(IllegalArgumentException.class, () ->{
		insumoService.atualizar(salvo.getId(), a);
	});
	
	assertEquals("Teste55", banco.getNome());
	assertEquals(2, banco.getQuantidade());
	
}

@Test
void validarAtualizarParcial() {
	Insumo i= new Insumo();
	i.setNome("Teste");
	i.setQuantidade(1);
	
	Insumo salvo = insumoService.salvar(i);
	
	Insumo nome = new Insumo();
	nome.setNome("Teste2");
	
	Insumo parcial = insumoService.atualizarParcial(salvo.getId(), nome);
	
	assertEquals(parcial.getNome(), "Teste2");
	assertEquals(parcial.getQuantidade(), 1);
	assertEquals(parcial.getId(), salvo.getId());
	
	Insumo banco = insumoRepository.findById(salvo.getId()).get();
	
	assertEquals("Teste2", banco.getNome());
	assertEquals(1, banco.getQuantidade());
	assertEquals(salvo.getId(), banco.getId());
		
}

@Test
void validarQuantidadeParcial() {
	Insumo i = new Insumo();
	i.setNome("Teste");
	i.setQuantidade(2);
	
	Insumo salvo = insumoService.salvar(i);
	
	Insumo q = new Insumo();
	q.setNome("Teste");
	q.setQuantidade(-10);
	
	Insumo banco = insumoRepository.findById(salvo.getId()).get();
	
	assertThrows(IllegalArgumentException.class, () -> {
		insumoService.atualizarParcial(salvo.getId(), q );
	});
	
	assertEquals("Teste", banco.getNome());
	assertEquals(2, banco.getQuantidade());
	assertEquals(salvo.getId(), banco.getId());	
}

@Test
void validarBuscarPorId() {
	Insumo i = new Insumo();
	i.setNome("Teste");
	i.setQuantidade(2);
	
	Insumo s = insumoService.salvar(i);
	
	Insumo b = insumoService.buscaporId(s.getId()).get();
	
	assertEquals("Teste", b.getNome());
	assertEquals(2, b.getQuantidade());
	assertEquals(s.getId(), b.getId());
}

@Test
void validarIdInvalido() {
	Long id = 333L;
	
	assertThrows(ResourceNotFoundException.class, () ->{
		insumoService.buscaporId(id);
	});
	

}

@Test
void validarDelete() {
	Insumo i = new Insumo();
	i.setNome("Teste");
	i.setQuantidade(1);
	
	Insumo s = insumoService.salvar(i);
	
	Boolean del = insumoService.remover(i.getId());
	
	Boolean v = insumoRepository.existsById(s.getId());
	
	 assertTrue(del);
	 assertFalse(v);
	
}

@Test
void validarIdDelete() {
	Long id = 999L;
	
	assertThrows(ResourceNotFoundException.class, () ->{
		insumoService.remover(id);
	});
	
	
}

@Test
void validarPageable() {
	
	Pageable pageable = PageRequest.of(0, 2);
	
	Page<Insumo> resultado = insumoService.listarTodos(pageable);
	
	assertEquals(insumoRepository.count(), resultado.getTotalElements());

}

@Test
void validarPageable100() {
	
	Pageable pageable = PageRequest.of(0 , 101);
	assertThrows(IllegalArgumentException.class, () -> {
		insumoService.listarTodos(pageable);
	});


}
	
}
