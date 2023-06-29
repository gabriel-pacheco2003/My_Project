package br.com.gabi_la_boutique.boutique.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BaseTests;
import br.com.gabi_la_boutique.boutique.models.Phone;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class PhoneServiceImpl extends BaseTests{

	@Autowired
	PhoneService phoneService;
	
	@Autowired
	ClientService clientService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/telefone.sql")
	void findByIdTest() {
		var phone = phoneService.findById(1);
		assertEquals(1, phone.getId());
		assertEquals("(11)11111-1111", phone.getNumber());
		assertEquals("Cliente 1", phone.getClient().getName());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> phoneService.findById(1));
		assertEquals("Número de telefone 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir número de telefone")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/telefone.sql")
	void insertPhoneTest() {
		Phone phone = new Phone(6, "(66)66666-6666", clientService.findById(1));
		phoneService.insert(phone);
		assertEquals(6, phone.getId());
		assertEquals("(66)66666-6666", phone.getNumber());
		assertEquals("Cliente 1", phone.getClient().getName());
	}
	
	@Test
	@DisplayName("Teste inserir número de telefone inválido")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/telefone.sql")
	void insertInvalidPhoneTest() {
		Phone phone = new Phone(6, "(66)66666-66666", clientService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> phoneService.insert(phone));
		assertEquals("Número de telefone incorreto", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir cliente inválido")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/telefone.sql")
	void insertInvalidClientTest() {
		Phone phone = new Phone(6, "(66)66666-6666", null);
		var exception = assertThrows(IntegrityViolation.class, () -> phoneService.insert(phone));
		assertEquals("Cliente inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/telefone.sql")
	void listAllTest() {
		assertEquals(5, phoneService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> phoneService.listAll());
		assertEquals("Nenhum número de telefone cadastrado", exception.getMessage());
	}

	
	@Test
	@DisplayName("Teste alterar número de telefone")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/telefone.sql")
	void updatePhoneTest() {
		assertEquals("(11)11111-1111", phoneService.findById(1).getNumber());
		Phone phoneUpdate = new Phone(1, "(11)11111-1112", clientService.findById(1));
		phoneService.update(phoneUpdate);
		assertEquals("(66)66666-6666", phoneService.findById(1).getNumber());
	}
	
	@Test
	@DisplayName("Teste alterar telefone com número duplicado")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/telefone.sql")
	void updatePhoneWithDuplicatedNumberTest() {
		assertEquals("(11)11111-1111", phoneService.findById(1).getNumber());
		Phone phoneUpdate = new Phone(null, "(22)22222-2222", clientService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> phoneService.insert(phoneUpdate));
		assertEquals("Número de telefone inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover número de telefone")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/telefone.sql")
	void deleteClientTest() {
		phoneService.delete(1);
		assertEquals(4, phoneService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover número de telefone inexistente")
	void deleteClientNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> phoneService.delete(1));
		assertEquals("Número de telefone 1 não encontrado", exception.getMessage());
	}
	
}
