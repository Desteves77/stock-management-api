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

import br.com.estoque.entity.Insumo;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.repository.InsumoRepository;

@ExtendWith(MockitoExtension.class)
public class InsumoServiceTest {

	@Mock
	private InsumoRepository insumoRepository;
	
	@InjectMocks
	private InsumoService insumoService;

	
	
@Test
void validarNome() {
	Insumo insumo = new Insumo();
	insumo.setNome("");
	insumo.setQuantidade(1);
	
	assertThrows(IllegalArgumentException.class, () -> {
		insumoService.salvar(insumo);
	});
	
	verifyNoInteractions(insumoRepository);
	
}

@Test
void validarQuantidade() {
	Insumo insumo= new Insumo ();
	insumo.setNome("A");
	insumo.setQuantidade(-1);
	

	assertThrows(IllegalArgumentException.class, () ->{
		insumoService.salvar(insumo);
	});
	
	verifyNoInteractions(insumoRepository);
	
}

@Test
void validarIdInexistente() {
	Insumo insumo = new Insumo ();
	insumo.setId(1L);
	insumo.setNome("A");
	insumo.setQuantidade(1);
	
	when(insumoRepository.existsById(2L)).thenReturn(false);

	assertThrows(ResourceNotFoundException.class, () ->{
		insumoRepository.findById(2L);
	});	
	
}



@Test
void validarIdExistente() {
	Insumo insumo= new Insumo();
	insumo.setId(1L);
	insumo.setNome("a");
	insumo.setQuantidade(1);
	
when(insumoRepository.existsById(1L)).thenReturn(true);
when(insumoRepository.findById(1L)).thenReturn(Optional.of(insumo));

Optional <Insumo> resultado = insumoService.buscaporId(1L);

assertTrue(resultado.isPresent());
assertEquals(1L, resultado.get().getId());
assertEquals("A", resultado.get().getNome());
assertEquals(1, resultado.get().getQuantidade());
}
	
@Test
void validarIdNulo() {
	
	assertThrows(IllegalArgumentException.class, () ->{
		insumoService.buscaporId(null);
	});
}

@Test
void validarInsumo() {
	Insumo insumo = new Insumo();
	insumo.setId(1L);
	insumo.setNome("A");
	insumo.setQuantidade(1);

	
	when(insumoRepository.save(insumo)).thenReturn(insumo);
	
	Insumo resultado = insumoService.salvar(insumo);
	
	assertNotNull(resultado);
	assertEquals(1L, resultado.getId());
	assertEquals("A", resultado.getNome());
	assertEquals(1, resultado.getQuantidade());
	
}
	
}
