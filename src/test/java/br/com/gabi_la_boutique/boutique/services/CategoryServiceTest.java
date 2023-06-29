package br.com.gabi_la_boutique.boutique.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BaseTests;
import br.com.gabi_la_boutique.boutique.models.Category;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class CategoryServiceTest extends BaseTests {

	@Autowired
	CategoryService categoryService;

	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/categoria.sql")
	void findByIdTest() {
		var category = categoryService.findById(1);
		assertEquals(1, category.getId());
		assertEquals("Categoria 1", category.getDescription());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> categoryService.findById(1));
		assertEquals("Categoria 1 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir categoria")
	void insertCategoryTest() {
		Category category = new Category(null, "insert");
		categoryService.insert(category);
		assertEquals(1, category.getId());
		assertEquals("insert", category.getDescription());
	}

	@Test
	@DisplayName("Teste inserir categoria já existente")
	@Sql("classpath:/resources/sqls/categoria.sql")
	void insertCategoryExistsTest() {
		Category category = new Category(null, "Categoria 1");
		var exception = assertThrows(IntegrityViolation.class, () -> categoryService.insert(category));
		assertEquals("Categoria já existente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir categoria nula")
	void insertNullCategpryTest() {
		Category category = new Category(null, null);
		var exception = assertThrows(IntegrityViolation.class, () -> categoryService.insert(category));
		assertEquals("Categoria inválida", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/categoria.sql")
	void listAllTest() {
		assertEquals(3, categoryService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> categoryService.listAll());
		assertEquals("Nenhuma categoria cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar categoria")
	@Sql("classpath:/resources/sqls/categoria.sql")
	void updateCategoryTest() {
		assertEquals("Categoria 1", categoryService.findById(1).getDescription());
		Category categoryUpdate = new Category(1, "update");
		categoryService.update(categoryUpdate);
		assertEquals("update", categoryService.findById(1).getDescription());
	}

	@Test
	@DisplayName("Teste alterar categoria com nome duplicado")
	@Sql("classpath:/resources/sqls/categoria.sql")
	void updateCategoryWithDuplicatedNameTest() {
		assertEquals("Categoria 1", categoryService.findById(1).getDescription());
		Category categoryUpdate = new Category(1, "Categoria 2");
		var exception = assertThrows(IntegrityViolation.class, () -> categoryService.update(categoryUpdate));
		assertEquals("Categoria já existente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover categoria")
	@Sql("classpath:/resources/sqls/categoria.sql")
	void deleteCategoryTest() {
		categoryService.delete(1);
		assertEquals(2, categoryService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover cateogria inexistente")
	void deleteCategoryNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> categoryService.delete(1));
		assertEquals("Categoria 1 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por descrição")
	@Sql("classpath:/resources/sqls/categoria.sql")
	void findByDescriptionTest() {
		assertEquals(3, categoryService.findByDescriptionContainingIgnoreCase("ca").size());
		assertEquals(1, categoryService.findByDescriptionContainingIgnoreCase("categoria 1").size());
	}

	@Test
	@DisplayName("Teste busca por descrição")
	@Sql("classpath:/resources/sqls/categoria.sql")
	void findByDescriptionNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> categoryService.findByDescriptionContainingIgnoreCase("fdilkn"));
		assertEquals("Nenhuma categoria encontrada", exception.getMessage());
	}

}