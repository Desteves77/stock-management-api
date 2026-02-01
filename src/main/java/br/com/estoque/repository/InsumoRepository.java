package br.com.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.estoque.entity.Insumo;


@Repository 

public interface InsumoRepository extends JpaRepository<Insumo,Long>{
	
}
	


