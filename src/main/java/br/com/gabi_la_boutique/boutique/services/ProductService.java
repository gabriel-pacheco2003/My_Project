package br.com.gabi_la_boutique.boutique.services;

import java.util.List;

import br.com.gabi_la_boutique.boutique.models.Category;
import br.com.gabi_la_boutique.boutique.models.Product;

public interface ProductService {

	Product findById(Integer id);

	Product insert(Product product);

	List<Product> listAll();

	Product update(Product product);

	void delete(Integer id);
	
	List<Product> findByNameContainingIgnoreCase(String name);

	List<Product> findByAmountBetween(Integer amountIn, Integer amountFin);

	List<Product> findByPriceOrderByPriceDesc(Double price);

	List<Product> findByPriceBetweenOrderByPriceDesc(Double priceIn, Double priceFin);

	List<Product> findByCategory(Category category);
	
}
