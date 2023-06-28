package br.com.gabi_la_boutique.boutique.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabi_la_boutique.boutique.models.Category;
import br.com.gabi_la_boutique.boutique.models.Product;
import br.com.gabi_la_boutique.boutique.services.CategoryService;
import br.com.gabi_la_boutique.boutique.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductResource {

	@Autowired
	private ProductService service;

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<Product> insert(@RequestBody Product product) {
		categoryService.findById(product.getCategory().getId());
		return ResponseEntity.ok(service.insert(product));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Product>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((phone) -> phone).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> update(@PathVariable Integer id, @RequestBody Product product) {
		product.setId(id);
		categoryService.findById(product.getCategory().getId());
		return ResponseEntity.ok(service.update(product));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Product> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Product>> findByNameContainingIgnoreCase(@PathVariable String name) {
		return ResponseEntity
				.ok(service.findByNameContainingIgnoreCase(name).stream().map((product) -> product).toList());
	}

	@GetMapping("/inicialAmount/{amountIn}/finalAmount/{amountFin}")
	public ResponseEntity<List<Product>> findByAmountBetween(@PathVariable Integer amountIn,
			@PathVariable Integer amountFin) {
		return ResponseEntity
				.ok(service.findByAmountBetween(amountIn, amountFin).stream().map((product) -> product).toList());
	}

	@GetMapping("/price/{price}")
	public ResponseEntity<List<Product>> findByPriceOrderByPriceDesc(@PathVariable Double price) {
		return ResponseEntity
				.ok(service.findByPriceOrderByPriceDesc(price).stream().map((product) -> product).toList());
	}
	
	@GetMapping("/inicialPrice/{priceIn}/finalPrice/{priceFin}")
	public ResponseEntity<List<Product>> findByPriceBetweenOrderByPriceDesc(@PathVariable Double priceIn,
			@PathVariable Double priceFin) {
		return ResponseEntity
				.ok(service.findByPriceBetweenOrderByPriceDesc(priceIn, priceFin).stream().map((product) -> product).toList());
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<Product>> findByCategory(@PathVariable Category categoryId) {
		return ResponseEntity.ok(service.findByCategory(categoryService.findById(categoryId.getId())));
	}

}
