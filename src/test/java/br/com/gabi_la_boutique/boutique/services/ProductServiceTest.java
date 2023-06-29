package br.com.gabi_la_boutique.boutique.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BaseTests;
import br.com.gabi_la_boutique.boutique.models.Product;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class ProductServiceTest extends BaseTests{

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByIdTest() {
		var product = productService.findById(1);
		assertEquals(1, product.getId());
		assertEquals("Produto 1", product.getName());
		assertEquals(10, product.getAmount());
		assertEquals(10.0, product.getPrice());
		assertEquals("Categoria 1", product.getCategory().getDescription());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> productService.findById(1));
		assertEquals("Produto 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir produto")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void insertProductTest() {
		Product product = new Product(1, "insert", 1, 1.0, categoryService.findById(2));
		productService.insert(product);
		assertEquals("insert", product.getName());
		assertEquals(1, product.getAmount());
		assertEquals(1.0, product.getPrice());
		assertEquals("Categoria 2", product.getCategory().getDescription());
	}

	@Test
	@DisplayName("Teste inserir nome nulo")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void insertNullNameTest() {
		Product product = new Product(null, null, 1, 1.0, categoryService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> productService.insert(product));
		assertEquals("Nome inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir quantidade nula")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void insertNullAmountTest() {
		Product product = new Product(null, "insert", null, 1.0, categoryService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> productService.insert(product));
		assertEquals("Quantidade inválida", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir preço nulo")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void insertNullPriceTest() {
		Product product = new Product(null, "insert", 1, null, categoryService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> productService.insert(product));
		assertEquals("Preço inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir categoria inválido")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void insertInvalidCategoryTest() {
		Product product = new Product(null, "insert", 1, 1.0, null);
		var exception = assertThrows(IntegrityViolation.class, () -> productService.insert(product));
		assertEquals("Categoria inválida", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void listAllTest() {
		assertEquals(3, productService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> productService.listAll());
		assertEquals("Nenhum produto cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar produto")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void updateProductTest() {
		assertEquals("Produto 1", productService.findById(1).getName());
		Product productUpdate = new Product(1, "update", 1, 1.0, categoryService.findById(1));
		productService.update(productUpdate);
		assertEquals("update", productService.findById(1).getName());
	}

	@Test
	@DisplayName("Teste alterar produto com quantidade menor que 0")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void updateProductWithInvalidAmountTest() {
		assertEquals(10, productService.findById(1).getAmount());
		Product productUpdate = new Product(1, "update", -1, 1.0, categoryService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> productService.insert(productUpdate));
		assertEquals("Quantidade inválida", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar produto com preço menor 1.0")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void updateProductWithInvalidPriceTest() {
		assertEquals(10.0, productService.findById(1).getPrice());
		Product productUpdate = new Product(1, "update", 1, 0.0, categoryService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> productService.insert(productUpdate));
		assertEquals("Preço inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover produto")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void deleteProductTest() {
		productService.delete(1);
		assertEquals(2, productService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover produto inexistente")
	void deleteProductNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> productService.delete(1));
		assertEquals("Produto 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por nome")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByNameTest() {
		assertEquals(3, productService.findByNameContainingIgnoreCase("pro").size());
		assertEquals(1, productService.findByNameContainingIgnoreCase("Produto 1").size());
	}

	@Test
	@DisplayName("Teste busca por nome inexistente")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> productService.findByNameContainingIgnoreCase("nsalkfja"));
		assertEquals("Nenhum produto encontrado com o nome nsalkfja", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por quantidade entre")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByAmountBetweenTest() {
		assertEquals(2, productService.findByAmountBetween(10, 20).size());
		assertEquals(1, productService.findByAmountBetween(1, 15).size());
	}
	
	@Test
	@DisplayName("Teste busca por quantidade entre ( não encontrado )")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByAmountBetweenNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> productService.findByAmountBetween(100, 200));
		assertEquals("Nenhum produto encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por preço")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByPriceTest() {
		assertEquals(1, productService.findByPriceOrderByPriceDesc(10.0).size());
	}
	
	@Test
	@DisplayName("Teste busca por preço não encontrado")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByPriceNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> productService.findByPriceOrderByPriceDesc(100.0));
		assertEquals("Nenhum produto encontrado com o preço 100.0", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por preço entre")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByPriceBetweenTest() {
		assertEquals(2, productService.findByPriceBetweenOrderByPriceDesc(10.0, 20.0).size());
		assertEquals(1, productService.findByPriceBetweenOrderByPriceDesc(1.0, 15.0).size());
	}
	
	@Test
	@DisplayName("Teste busca por preço entre ( não encontrado )")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByPriceBetweenNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> productService.findByPriceBetweenOrderByPriceDesc(100.0, 200.0));
		assertEquals("Nenhum produto encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por categoria")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByCategoryTest() {
		assertEquals(2, productService.findByCategory(categoryService.findById(1)).size());
	}

	@Test
	@DisplayName("Teste busca por categoria inexistente")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	void findByCategoryNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> productService.findByCategory(categoryService.findById(3)));
		assertEquals("Nenhum produto encontrado", exception.getMessage());
	}
	
}
