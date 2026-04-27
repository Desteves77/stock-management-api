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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.estoque.entity.Insumo;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.service.InsumoService;

@WebMvcTest(InsumoController.class)
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
					"nome": teste,
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
					"nome": teste,
					"quantidade": 10
				}
					"""))
	.andExpect(status().isCreated());
	
}


@Test
void validarNomePut() throws Exception {
	mockMvc.perform(put("/insumos")
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
	mockMvc.perform(put("/insumos")
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
	salvo.setId(1L);
	
	when(insumoService.salvar(any(Insumo.class))).thenReturn(salvo);
	
	mockMvc.perform(put("/insumos")
			.contentType(MediaType.APPLICATION_JSON)
			.content("""
				{
					"nome": teste,
					"quantidade": 10
				}
					"""))
	.andExpect(status().isOk());
	
}

@Test
void validarNomePatch() throws Exception {
	mockMvc.perform(patch("/insumos")
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
	mockMvc.perform(patch("/insumos")
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
	
	mockMvc.perform(patch("/insumos")
			.contentType(MediaType.APPLICATION_JSON)
			.content("""
				{
					"nome": teste,
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
	insumo.setNome("Teste");
	insumo.setQuantidade(1);
	insumo.setId(1L);
	
	when(insumoService.buscaporId(1L)).thenReturn(Optional.of(insumo));
	
	mockMvc.perform(get("/insumo/1L"))
	.andExpect(status().isOk())
	.andExpect(jsonPath("$.id").value(1))
	.andExpect(jsonPath("$.nome").value("teste"))
	.andExpect(jsonPath("$.quantidade").value(1));
	
}

@Test
void validarIdDelete() throws Exception{
	when(insumoService.remover(2L)).thenThrow(ResourceNotFoundException.class);
	
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
	
	when(insumoService.remover(1L)).thenReturn(true);
	
	mockMvc.perform(delete("/insumos/1L"))
	.andExpect(status().isOk());
	
}





	
}
