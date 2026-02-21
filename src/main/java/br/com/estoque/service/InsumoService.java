package br.com.estoque.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.estoque.entity.Insumo;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.repository.InsumoRepository;


@Service
public class InsumoService {private final InsumoRepository repository;

public InsumoService(InsumoRepository repository) {
	this.repository = repository;
}
	

public Insumo salvar(Insumo insumo) {
	if (insumo.getNome()==null|| insumo.getNome().isBlank()) {
		throw new IllegalArgumentException("Nome não pode ser vazio");
	}
	
	if(insumo.getQuantidade()==null||insumo.getQuantidade()<0) {
		throw new IllegalArgumentException("Quantidade não pode ser negativa");
	}
	return repository.save(insumo);
}
	
public Insumo atualizar(Long id, Insumo novoInsumo) {
	if (novoInsumo.getNome()==null|| novoInsumo.getNome().isBlank()) {
		throw new IllegalArgumentException("Nome não pode ser vazio");
	}
	
	if(novoInsumo.getQuantidade()==null||novoInsumo.getQuantidade()<0) {
		throw new IllegalArgumentException("Quantidade não pode ser negativa");
	}
	
	Insumo existente = repository.findById(id)
	.orElseThrow(()-> new ResourceNotFoundException("Insumo não encontrado"));
	
	existente.setNome(novoInsumo.getNome());
	existente.setQuantidade(novoInsumo.getQuantidade());

	return repository.save(existente);
	
}


public Insumo atualizarParcial(Long id, Insumo parcial) {
	 if (id == null) {
	        throw new IllegalArgumentException("Id inválido");
	    }
	 
	Insumo existente = repository.findById(id)
	.orElseThrow(()->new ResourceNotFoundException("Insumo não encontrado"));
	
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



public Page<Insumo> listarTodos(Pageable pageable){
	
	 if (pageable.getPageNumber() < 0) {
		    throw new IllegalArgumentException("page não pode ser negativo");
		  }

		  if (pageable.getPageSize() <= 0 || pageable.getPageSize() > 100) {
		    throw new IllegalArgumentException("size deve estar entre 1 e 100");
		  }
	
	return repository.findAll(pageable);
}


public Optional<Insumo> buscaporId(Long id) {
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
