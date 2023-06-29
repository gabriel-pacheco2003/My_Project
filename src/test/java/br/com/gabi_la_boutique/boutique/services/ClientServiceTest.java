package br.com.gabi_la_boutique.boutique.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BaseTests;
import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class ClientServiceTest extends BaseTests {

	@Autowired
	ClientService clientService;

	@Autowired
	AddressService addressService;

	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void findByIdTest() {
		var client = clientService.findById(1);
		assertEquals(1, client.getId());
		assertEquals("Cliente 1", client.getName());
		assertEquals("Email1", client.getEmail());
		assertEquals("Rua 1", client.getAddress().getStreet());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.findById(1));
		assertEquals("Cliente 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir cliente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void insertClientTest() {
		Client client = new Client(1, "insert", "insert", addressService.findById(1));
		clientService.insert(client);
		assertEquals(1, client.getId());
		assertEquals("insert", client.getName());
		assertEquals("insert", client.getEmail());
		assertEquals("Rua 1", client.getAddress().getStreet());
	}

	@Test
	@DisplayName("Teste inserir nome nulo")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void insertNullNameTest() {
		Client client = new Client(null, null, "insert", addressService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(client));
		assertEquals("Nome inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir email nulo")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void insertNullEmailTest() {
		Client client = new Client(null, "insert", null, addressService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(client));
		assertEquals("Email inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir endereço inválido")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void insertInvalidAddressTest() {
		Client client = new Client(null, "insert", "insert", null);
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(client));
		assertEquals("Endereço inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void listAllTest() {
		assertEquals(3, clientService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.listAll());
		assertEquals("Nenhum cliente cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar cliente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void updateClientTest() {
		assertEquals("Cliente 1", clientService.findById(1).getName());
		Client clientUpdate = new Client(1, "update", "update", addressService.findById(1));
		clientService.update(clientUpdate);
		assertEquals("update", clientService.findById(1).getName());
	}

	@Test
	@DisplayName("Teste alterar cliente com email duplicado")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void updateClientWithDuplicatedEmailTest() {
		assertEquals("Email1", clientService.findById(1).getEmail());
		Client clientUpdate = new Client(1, "update", "Email2", addressService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> clientService.insert(clientUpdate));
		assertEquals("Email já existente", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover cliente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void deleteClientTest() {
		clientService.delete(1);
		assertEquals(2, clientService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover cliente inexistente")
	void deleteClientNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> clientService.delete(1));
		assertEquals("Cliente 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por nome")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void findByNameTest() {
		assertEquals(3, clientService.findByNameContainingIgnoreCase("cli").size());
		assertEquals(1, clientService.findByNameContainingIgnoreCase("Cliente 1").size());
	}

	@Test
	@DisplayName("Teste busca por nome inexistente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void findByNameNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> clientService.findByNameContainingIgnoreCase("ligkesa"));
		assertEquals("Nenhum cliente encontrado com o nome ligkesa", exception.getMessage());
	}

	@Test
	@DisplayName("Teste busca por email")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void findByEmailTest() {
		assertEquals(3, clientService.findByEmailContainingIgnoreCase("Em").size());
		assertEquals(1, clientService.findByEmailContainingIgnoreCase("Email1").size());
	}

	@Test
	@DisplayName("Teste busca por email inexistente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void findByEmailNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> clientService.findByEmailContainingIgnoreCase("kjlnslkfa"));
		assertEquals("Email inválido", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por endereço")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void findByAddressTest() {
		assertEquals(2, clientService.findByAddressOrderByName(addressService.findById(1)).size());
	}

	@Test
	@DisplayName("Teste busca por endereço inexistente")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	void findByAddressNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> clientService.findByAddressOrderByName(addressService.findById(3)));
		assertEquals("Nenhum cliente encontrado", exception.getMessage());
	}

}
