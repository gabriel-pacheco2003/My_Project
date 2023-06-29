package br.com.gabi_la_boutique.boutique.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BaseTests;
import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;
import br.com.gabi_la_boutique.boutique.utils.DateUtils;
import jakarta.transaction.Transactional;

@Transactional
public class SellServiceTest extends BaseTests{

	@Autowired
	SellService sellService;
	
	@Autowired
	ClientService clientService;
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	void findByIdTest() {
		var sell = sellService.findById(1);
		assertEquals(1, sell.getId());
		assertEquals("Cliente 1", sell.getClient().getName());
		assertEquals("10/01/2023", sell.getDate());
	}
	
	@Test
	@DisplayName("Teste busca por ID inexistente")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> sellService.findById(1));
		assertEquals("Cidade 1 não encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir venda")
	void insertSellTest() {
		Sell sell = new Sell(null, clientService.findById(1), DateUtils.stringToDate("01/01/2023"));
		sellService.insert(sell);
		assertEquals(1, sell.getId());
		assertEquals("Cliente 1", sell.getClient().getName());
		assertEquals("01/01/2023", sell.getDate());
	}
	
	@Test
	@DisplayName("Teste inserir cliente nulo")
	void insertNullClientTest() {
		Sell sell = new Sell(null, null, DateUtils.stringToDate("01/01/2023"));
		var exception = assertThrows(IntegrityViolation.class, () -> sellService.insert(sell));
		assertEquals("Cliente inválida", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	void listAllTest() {
		assertEquals(3, sellService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> sellService.listAll());
		assertEquals("Nenhuma venda cadastrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar venda")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	void updateCityTest() {
		assertEquals("10/01/2023", sellService.findById(1).getDate());
		Sell sellUpdate = new Sell(1, clientService.findById(1), DateUtils.);
		sellService.update(sellUpdate);
		assertEquals("update", sellService.findById(1).getName());
	}

	@Test
	@DisplayName("Teste alterar cidade com nome duplicado")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	void updateCityWithDuplicatedNameTest() {
		assertEquals("Tubarão", sellService.findById(1).getName());
		City cityUpdate = new City(1, "Camacho");
		var exception = assertThrows(IntegrityViolation.class, () -> cityService.update(cityUpdate));
		assertEquals("Cidade já existente", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste remover cidade")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	void deleteCityTest() {
		sellService.delete(1);
		assertEquals(2, sellService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover cidade inexistente")
	void deleteCityNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> sellService.delete(1));
		assertEquals("Cidade 1 não encontrada", exception.getMessage());
	}
	
}
