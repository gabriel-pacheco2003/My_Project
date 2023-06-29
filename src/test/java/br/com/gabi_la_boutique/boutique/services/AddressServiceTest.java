package br.com.gabi_la_boutique.boutique.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BaseTests;
import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class AddressServiceTest extends BaseTests {

	@Autowired
	AddressService addressService;

	@Autowired
	CityService cityService;

	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByIdTest() {
		var address = addressService.findById(1);
		assertEquals(1, address.getId());
		assertEquals("Rua 1", address.getStreet());
		assertEquals("Bairro 1", address.getNeighborhood());
		assertEquals("Tubarão", address.getCity().getName());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> addressService.findById(1));
		assertEquals("Endereço 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir endereço")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void insertAddressTest() {
		Address address = new Address(null, "insert", "insert", cityService.findById(1));
		addressService.insert(address);
		assertEquals(1, address.getId());
		assertEquals("insert", address.getStreet());
		assertEquals("insert", address.getNeighborhood());
		assertEquals("Tubarão", address.getCity().getName());
	}

	@Test
	@DisplayName("Teste inserir rua nula")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void insertNullStreetTest() {
		Address address = new Address(null, null, "insert", cityService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> addressService.insert(address));
		assertEquals("Rua inválida", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir bairro nulo")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void insertNullNeighborhoodTest() {
		Address address = new Address(null, "insert", null, cityService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> addressService.insert(address));
		assertEquals("Bairro inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir cidade inválida")
	@Sql("classpath:/resources/sqls/cidade.sql")
	void insertInvalidCityTest() {
		Address address = new Address(null, "insert", "insert", null);
		var exception = assertThrows(IntegrityViolation.class, () -> addressService.insert(address));
		assertEquals("Cidade inválida", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void listAllTest() {
		assertEquals(3, cityService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> addressService.listAll());
		assertEquals("Nenhum endereço cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar endereço")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void updateAddressTest() {
		assertEquals("Rua 1", addressService.findById(1).getStreet());
		Address addressUpdate = new Address(1, "update", "update", cityService.findById(1));
		addressService.update(addressUpdate);
		assertEquals("update", addressService.findById(1).getStreet());
	}

	@Test
	@DisplayName("Teste remover endereço")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void deleteAddressTest() {
		addressService.delete(1);
		assertEquals(2, addressService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover endereço inexistente")
	void deleteAddressNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> addressService.delete(1));
		assertEquals("Endereço 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por rua")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByStreetTest() {
		assertEquals(3, addressService.findByStreetContainingIgnoreCase("ru").size());
		assertEquals(1, addressService.findByStreetContainingIgnoreCase("Rua 1").size());
	}

	@Test
	@DisplayName("Teste busca por rua inexistente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByStreetNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> addressService.findByStreetContainingIgnoreCase("sakjdhg"));
		assertEquals("Nenhum endereço encontrado com a rua sakjdhg", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por bairro")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByNeighbothoodTest() {
		assertEquals(3, addressService.findByNeighborhoodContainingIgnoreCase("bai").size());
		assertEquals(1, addressService.findByNeighborhoodContainingIgnoreCase("Bairro 1").size());
	}

	@Test
	@DisplayName("Teste busca por bairro inexistente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByNeighborhoodNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> addressService.findByNeighborhoodContainingIgnoreCase("safiilg"));
		assertEquals("Nenhum endereço encontrado com o bairro safiilg", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por rua e bairro")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByStreetAndNeighbothoodTest() {
		assertEquals(3,
				addressService.findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase("ru", "bai").size());
		assertEquals(1, addressService
				.findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase("Rua 1", "Bairro 1").size());
	}

	@Test
	@DisplayName("Teste busca por rua e bairro inexistentes")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByStreetAndNeighbothoodNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> addressService
				.findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase("nsaklif", "abskfr"));
		assertEquals("Nenhum endereço encontrado com a rua nsaklif e o bairro abskfr", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca endereço por cidade")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByCityTest() {
		assertEquals(2, addressService.findByCity(cityService.findById(2)).size());
	}

	@Test
	@DisplayName("Teste busca endereço por cidade inexistente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	void findByCityNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> addressService.findByCity(cityService.findById(3)));
		assertEquals("Nenhum endereço encontrado", exception.getMessage());
	}

}
