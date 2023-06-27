package br.com.gabi_la_boutique.boutique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabi_la_boutique.boutique.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	List<Category> findByDescriptionContainingIgnoreCase(String description);
	
	Category findByDescription(String description);
	
}
