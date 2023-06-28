package br.com.gabi_la_boutique.boutique.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.Category;
import br.com.gabi_la_boutique.boutique.models.Product;
import br.com.gabi_la_boutique.boutique.repositories.ProductRepository;
import br.com.gabi_la_boutique.boutique.services.ProductService;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	private void validateProduct(Product product) {
		if(product.getName() == null) {
			throw new IntegrityViolation("Nome inválido");
		}
		
		if(product.getAmount() == null || product.getAmount() < 0) {
			throw new IntegrityViolation("Quantidade inválido");
		}
		
		if(product.getPrice() == null || product.getPrice() < 0) {
			throw new IntegrityViolation("Preço inválido");
		}
		
		if(product.getCategory() == null) {
			throw new IntegrityViolation("Categoria inválido");
		}
	}
	
	@Override
	public Product findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Produto %s não encontrado".formatted(id)));
	}

	@Override
	public Product insert(Product product) {
		validateProduct(product);
		return repository.save(product);
	}

	@Override
	public List<Product> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhum produto cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public Product update(Product product) {
		validateProduct(product);
		return repository.save(product);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Product> findByNameContainingIgnoreCase(String name) {
		if (repository.findByNameContainingIgnoreCase(name).isEmpty()) {
			throw new ObjectNotFound("Nenhum produto encontrado com o nome %s".formatted(name));
		}
		return repository.findByNameContainingIgnoreCase(name);
	}

	@Override
	public List<Product> findByAmountBetween(Integer amountIn, Integer amountFin) {
		if (repository.findByAmountBetween(amountIn, amountFin).isEmpty()) {
			throw new ObjectNotFound("Nenhum produto encontrado");
		}
		return repository.findByAmountBetween(amountIn, amountFin);
	}

	@Override
	public List<Product> findByPriceOrderByPriceDesc(Double price) {
		if (repository.findByPriceOrderByPriceDesc(price).isEmpty()) {
			throw new ObjectNotFound("Nenhum produto encontrado com o preço %s".formatted(price));
		}
		return repository.findByPriceOrderByPriceDesc(price);
	}

	@Override
	public List<Product> findByPriceBetweenOrderByPriceDesc(Double priceIn, Double priceFin) {
		if (repository.findByPriceBetweenOrderByPriceDesc(priceIn, priceFin).isEmpty()) {
			throw new ObjectNotFound("Nenhum produto encontrado");
		}
		return repository.findByPriceBetweenOrderByPriceDesc(priceIn, priceFin);
	}

	@Override
	public List<Product> findByCategory(Category category) {
		if (repository.findByCategory(category).isEmpty()) {
			throw new ObjectNotFound("Nenhum produto encontrado com a categoria %s".formatted(category));
		}
		return repository.findByCategory(category);
	}

}
