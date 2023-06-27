package br.com.gabi_la_boutique.boutique.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.Category;
import br.com.gabi_la_boutique.boutique.repositories.CategoryRepository;
import br.com.gabi_la_boutique.boutique.services.CategoryService;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;

	private void validateCategory(Category category) {
		Category find = repository.findByDescription(category.getDescription());
		if (find != null && find.getId() != category.getId()) {
			throw new IntegrityViolation("Categoria já existente");
		}
	}

	@Override
	public Category findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Categoria %s não encontrada".formatted(id)));
	}

	@Override
	public Category insert(Category category) {
		validateCategory(category);
		return repository.save(category);
	}

	@Override
	public List<Category> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhuma categoria cadastrada");
		}
		return repository.findAll();
	}

	@Override
	public Category update(Category category) {
		validateCategory(category);
		return repository.save(category);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Category> findByDescriptionContainingIgnoreCase(String description) {
		if (repository.findByDescriptionContainingIgnoreCase(description).isEmpty()) {
			throw new ObjectNotFound("Nenhuma categoria encontrada com a descrição %s".formatted(description));
		}
		return repository.findByDescriptionContainingIgnoreCase(description);
	}

}
