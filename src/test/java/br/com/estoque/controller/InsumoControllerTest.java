package br.com.estoque.controller;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.estoque.entity.Insumo;
import br.com.estoque.exception.GlobalHandlerException;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.service.InsumoService;

@WebMvcTest(InsumoController.class)
@Import(GlobalHandlerException.class)
public class InsumoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private InsumoService insumoService;
	
	
	
@Test
void validarNomePost() throws Exception {
	mockMvc.perform(post("/insumos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "nome": "",
                  "quantidade": 10
                }
            """))
        .andExpect(status().isBadRequest());
}

@Test 
void validarQuantidadePost() throws Exception{
	mockMvc.perform(post("/insumos")
			.contentType(MediaType.APPLICATION_JSON)
			.content("""
				{
					"nome": "teste",
					"quantidade": -10
				}
					"""))
	.andExpect(status().isBadRequest());
}
	
@Test
void validarSucessoPost() throws Exception{
	Insumo salvo = new Insumo();
	salvo.setNome("teste");
	salvo.setQuantidade(10);
	salvo.setId(1L);
	
	when(insumoService.salvar(any(Insumo.class))).thenReturn(salvo);
	
	mockMvc.perform(post("/insumos")
			.contentType(MediaType.APPLICATION_JSON)
			.content("""
				{
					"nome": "teste",
					"quantidade": 10
				}
					"""))
	.andExpect(status().isCreated());
	
}


@Test
void validarNomePut() throws Exception {
	mockMvc.perform(put("/insumos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "nome": "",
                  "quantidade": 10
                }
            """))
        .andExpect(status().isBadRequest());
}

@Test
void validarQuantidadePut() throws Exception {
	mockMvc.perform(put("/insumos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "nome": "teste",
                  "quantidade": -10
                }
            """))
        .andExpect(status().isBadRequest());
}

@Test
void validarSucessoPut() throws Exception{
	Insumo salvo = new Insumo();
	salvo.setNome("teste");
	salvo.setQuantidade(10);
	
	when(insumoService.salvar(any(Insumo.class))).thenReturn(salvo);
	
	mockMvc.perform(put("/insumos/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content("""
				{
					"nome": "teste1",
					"quantidade": 11
				}
					"""))
	.andExpect(status().isOk());
	
}

@Test
void validarNomePatch() throws Exception {
	mockMvc.perform(patch("/insumos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "nome": "",
                  "quantidade": 10
                }
            """))
        .andExpect(status().isBadRequest());
}

@Test
void validarQuantidadePatch() throws Exception {
	mockMvc.perform(patch("/insumos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                  "nome": "teste",
                  "quantidade": -10
                }
            """))
        .andExpect(status().isBadRequest());
}

@Test
void validarSucessoPatch() throws Exception{
	Insumo salvo = new Insumo();
	salvo.setNome("teste");
	salvo.setQuantidade(10);
	salvo.setId(1L);
	
	when(insumoService.salvar(any(Insumo.class))).thenReturn(salvo);
	
	mockMvc.perform(patch("/insumos/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content("""
				{
					"nome": "teste",
					"quantidade": 10
				}
					"""))
	.andExpect(status().isOk());
	
}

@Test
void validarIdGet() throws Exception{
	when(insumoService.buscaporId(2L)).thenThrow(ResourceNotFoundException.class);
	
	mockMvc.perform(get("/insumos/2"))
	.andExpect(status().isNotFound());
}

@Test
void validarFormatoIdGet() throws Exception{
	mockMvc.perform(get("/insumos/abc"))
	.andExpect(status().isBadRequest());
}

@Test
void validarSucessoGet() throws Exception{
	Insumo insumo = new Insumo();
	insumo.setNome("teste");
	insumo.setQuantidade(1);
	insumo.setId(1L);
	
	when(insumoService.buscaporId(insumo.getId())).thenReturn(Optional.of(insumo));
	
	mockMvc.perform(get("/insumos/1"))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$.id").value(1))
	.andExpect(jsonPath("$.nome").value("teste"))
	.andExpect(jsonPath("$.quantidade").value(1));
	
}

@Test
void validarIdDelete() throws Exception{
	when(insumoService.remover(2L)).thenThrow(new ResourceNotFoundException("Id não encontrado"));
	
	mockMvc.perform(delete("/insumos/2"))
	.andExpect(status().isNotFound());
}

@Test
void validarFormatoIdDelete() throws Exception{
	mockMvc.perform(delete("/insumos/abc"))
	.andExpect(status().isBadRequest());
}

@Test
void validarSucessoDelete() throws Exception{
	Insumo insumo = new Insumo();
	insumo.setNome("Teste");
	insumo.setQuantidade(1);
	insumo.setId(1L);
	
	when(insumoService.remover(insumo.getId())).thenReturn(true);
	
	mockMvc.perform(delete("/insumos/1"))
	.andExpect(status().isNoContent());
	
}





	
}
