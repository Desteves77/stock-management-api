package br.com.estoque.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.estoque.entity.Produto;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.repository.ProdutoRepository;


@Service 

public class ProdutoService {
	
private final ProdutoRepository repository;

public ProdutoService(ProdutoRepository repository) {
	this.repository = repository;
}
	

public Produto salvar(Produto produto) {
	if (produto.getNome()==null|| produto.getNome().isBlank()) {
		throw new IllegalArgumentException("Nome não pode ser vazio");
	}
	
	if(produto.getQuantidade()==null||produto.getQuantidade()<0) {
		throw new IllegalArgumentException("Quantidade não pode ser negativa");
	}
	return repository.save(produto);
}

public Produto atualizar(Long id, Produto novoProduto) {
	if (novoProduto.getNome()==null|| novoProduto.getNome().isBlank()) {
		throw new IllegalArgumentException("Nome não pode ser vazio");
	}
	
	if(novoProduto.getQuantidade()==null||novoProduto.getQuantidade()<0) {
		throw new IllegalArgumentException("Quantidade não pode ser negativa");
	}
	
	Produto existente = repository.findById(id)
	.orElseThrow(()-> new ResourceNotFoundException("Produto não encontrado"));
	
	existente.setNome(novoProduto.getNome());
	existente.setQuantidade(novoProduto.getQuantidade());

	return repository.save(existente);
	
}


public Produto atualizarParcial(Long id, Produto parcial) {
	
	 if (id == null) {
	        throw new IllegalArgumentException("Id inválido");
	    }
	 
	Produto existente = repository.findById(id)
	.orElseThrow(()->new ResourceNotFoundException("Produto não encontrado"));
	
	if(!(parcial.getNome()== null)) {
		if((parcial.getNome().isEmpty())) {
			throw new IllegalArgumentException("Nome não pode ser vazio");
		}
		existente.setNome(parcial.getNome());
	}
	
	if(!(parcial.getQuantidade()==null)) {
		if(parcial.getQuantidade()<0) {
			throw new IllegalArgumentException("Quantidade não pode ser negativa");
		}
		existente.setQuantidade(parcial.getQuantidade());
	}
	
	return repository.save(existente);
}
	
public Page<Produto> listarTodos(Pageable pageable){
	
	  if (pageable.getPageNumber() < 0) {
		    throw new IllegalArgumentException("page não pode ser negativo");
		  }

		  if (pageable.getPageSize() <= 0 || pageable.getPageSize() > 100) {
		    throw new IllegalArgumentException("size deve estar entre 1 e 100");
		  }
	
	
	return repository.findAll(pageable);
}


public Optional<Produto> buscaporId(Long id) {
	if(id == null) {
		throw new IllegalArgumentException("id inválido");
	}
	
	if(!repository.existsById(id)) {
		throw new ResourceNotFoundException("Id não encontrado");
	}
	
	return repository.findById(id);
}

public boolean remover(Long id) {
	if(id== null) {
		throw new IllegalArgumentException("Id inválido");
	}
	
	if(!repository.existsById(id)) {
		throw new ResourceNotFoundException("Id não encontrado");
	}
	repository.deleteById(id);
	return true;
}








}
