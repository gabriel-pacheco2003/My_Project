package br.com.gabi_la_boutique.boutique.services;

import java.util.List;

import br.com.gabi_la_boutique.boutique.models.Category;

public interface CategoryService {
	
	Category findById(Integer id);

	Category insert(Category category);

	List<Category> listAll();

	Category update(Category category);

	void delete(Integer id);
	
	List<Category> findByDescriptionContainingIgnoreCase(String description);

}
