package br.com.gabi_la_boutique.boutique.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BaseTests;
import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class CityServiceTest extends BaseTests{
	
	@Autowired
	CityService cityService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void findByIdTest() {
		var city = cityService.findById(1);
		assertEquals(1, city.getId());
		assertEquals("Tubarão", city.getName());
	}
	
	@Test
	@DisplayName("Teste busca por ID inexistente")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> cityService.findById(1));
		assertEquals("Cidade 1 não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir cidade")
	void insertCityTest() {
		City city = new City(null, "insert");
		cityService.insert(city);
		assertEquals(1, city.getId());
		assertEquals("insert", city.getName());
	}
	
	@Test
	@DisplayName("Teste inserir cidade já existente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void insertCityExistsTest() {
		City city = new City(null, "Tubarão");
		var exception = assertThrows(IntegrityViolation.class, () -> cityService.insert(city));
		assertEquals("Cidade já existente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir cidade nula")
	void insertNullCityTest() {
		City city = new City(null, null);
		var exception = assertThrows(IntegrityViolation.class, () -> cityService.insert(city));
		assertEquals("Cidade inválida", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void listAllTest() {
		assertEquals(3, cityService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> cityService.listAll());
		assertEquals("Nenhuma cidade cadastrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar cidade")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void updateCityTest() {
		assertEquals("Tubarão", cityService.findById(1).getName());
		City cityUpdate = new City(1, "update");
		cityService.update(cityUpdate);
		assertEquals("update", cityService.findById(1).getName());
	}

	@Test
	@DisplayName("Teste alterar cidade com nome duplicado")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void updateCityWithDuplicatedNameTest() {
		assertEquals("Tubarão", cityService.findById(1).getName());
		City cityUpdate = new City(1, "Camacho");
		var exception = assertThrows(IntegrityViolation.class, () -> cityService.update(cityUpdate));
		assertEquals("Cidade já existente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste remover cidade")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void deleteCityTest() {
		cityService.delete(1);
		assertEquals(2, cityService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover cidade inexistente")
	void deleteCityNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> cityService.delete(1));
		assertEquals("Cidade 1 não encontrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por nome")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void findByNameTest() {
		assertEquals(2, cityService.findByNameContainingIgnoreCase("ca").size());
		assertEquals(1, cityService.findByNameContainingIgnoreCase("Tubarão").size());
	}

	@Test
	@DisplayName("Teste busca por nome inexistente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> cityService.findByNameContainingIgnoreCase("sankjsc"));
		assertEquals("Nenhuma cidade encontrada com o nome sankjsc", exception.getMessage());
	}
	
}
