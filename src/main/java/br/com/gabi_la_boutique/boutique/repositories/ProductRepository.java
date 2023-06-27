package br.com.gabi_la_boutique.boutique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabi_la_boutique.boutique.models.Category;
import br.com.gabi_la_boutique.boutique.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByNameContainingIgnoreCase(String name);

	List<Product> findByAmountBetween(Integer amountIn, Integer amountFin);

	List<Product> findByPriceOrderByPriceDesc(Double price);

	List<Product> findByPriceBetweenOrderByPriceDesc(Double priceIn, Double priceFin);

	List<Product> findByCategory(Category category);

}
