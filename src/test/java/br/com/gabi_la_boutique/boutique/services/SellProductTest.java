package br.com.gabi_la_boutique.boutique.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.gabi_la_boutique.boutique.BaseTests;
import br.com.gabi_la_boutique.boutique.models.SellProduct;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class SellProductTest extends BaseTests {

	@Autowired
	SellProductService sellProductService;

	@Autowired
	SellService sellService;

	@Autowired
	ProductService productService;

	@Test
	@DisplayName("Teste busca por ID")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void findByIdTest() {
		var sellProduct = sellProductService.findById(1);
		assertEquals(1, sellProduct.getId());
		assertEquals("Cliente 1", sellProduct.getSell().getClient().getName());
		assertEquals("Produto 1", sellProduct.getProduct().getName());
		assertEquals(3, sellProduct.getAmountSell());
	}

	@Test
	@DisplayName("Teste busca por ID inexistente")
	void findByIdNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> sellProductService.findById(1));
		assertEquals("Venda/Produto 1 não encontrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir venda/produto")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void insertSellProductTest() {
		SellProduct sellProduct = new SellProduct(1, sellService.findById(1), productService.findById(1), 10);
		sellProductService.insert(sellProduct);
		assertEquals(1, sellProduct.getId());
		assertEquals("Cliente 1", sellProduct.getSell().getClient().getName());
		assertEquals("Produto 1", sellProduct.getProduct().getName());
		assertEquals(10, sellProduct.getAmountSell());
	}

	@Test
	@DisplayName("Teste inserir venda nula")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void insertNullSellTest() {
		SellProduct sellProduct = new SellProduct(1, null, productService.findById(1), 10);
		var exception = assertThrows(IntegrityViolation.class, () -> sellProductService.insert(sellProduct));
		assertEquals("Venda inválida", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir produto nulo")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void insertNullProductTest() {
		SellProduct sellProduct = new SellProduct(1, sellService.findById(1), null, 10);
		var exception = assertThrows(IntegrityViolation.class, () -> sellProductService.insert(sellProduct));
		assertEquals("Produto inválido", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir quantidade nula")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void insertNullAmountSellTest() {
		SellProduct sellProduct = new SellProduct(1, sellService.findById(1), productService.findById(1), null);
		var exception = assertThrows(IntegrityViolation.class, () -> sellProductService.insert(sellProduct));
		assertEquals("Quantidade inválida", exception.getMessage());
	}

	@Test
	@DisplayName("Teste listar todos")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void listAllTest() {
		assertEquals(3, sellService.listAll().size());
	}

	@Test
	@DisplayName("Teste listar todos sem cadastros existentes")
	void listAllNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> sellProductService.listAll());
		assertEquals("Nenhuma Venda/Produto cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste alterar venda/produto")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void updateSellTest() {
		assertEquals(3, sellProductService.findById(1).getAmountSell());
		SellProduct sellProductUpdate = new SellProduct(1, sellService.findById(1), productService.findById(1), 5);
		sellProductService.update(sellProductUpdate);
		assertEquals(5, sellProductService.findById(1).getAmountSell());
	}

	@Test
	@DisplayName("Teste alterar venda/produto com quantidade menor que o esperado")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void updateSellProductWithInvalidAmountTest() {
		assertEquals(3, sellProductService.findById(1).getAmountSell());
		SellProduct sellProductUpdate = new SellProduct(1, sellService.findById(1), productService.findById(1), -1);
		var exception = assertThrows(IntegrityViolation.class, () -> sellProductService.update(sellProductUpdate));
		assertEquals("Quantidade inválida", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar venda/produto com quantidade maior que o estoque")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void updateSellProductWithQuantityGreaterThanStockTest() {
		assertEquals(3, sellProductService.findById(1).getAmountSell());
		SellProduct sellProductUpdate = new SellProduct(1, sellService.findById(1), productService.findById(1), 100);
		var exception = assertThrows(IntegrityViolation.class, () -> sellProductService.update(sellProductUpdate));
		assertEquals("Estoque menor que a quantidade requerida", exception.getMessage());
	}

	@Test
	@DisplayName("Teste remover venda/produto")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void deleteSellTest() {
		sellProductService.delete(1);
		assertEquals(2, sellProductService.listAll().size());
	}

	@Test
	@DisplayName("Teste remover venda/produto inexistente")
	void deleteSellNonExistsTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> sellProductService.delete(1));
		assertEquals("Venda/Produto 1 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por venda")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void findBySellTest() {
		assertEquals(2, sellProductService.findBySellOrderByAmountSell(sellService.findById(2)).size());
	}

	@Test
	@DisplayName("Teste busca por venda não encontrada")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void findBySellNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> sellProductService.findBySellOrderByAmountSell(sellService.findById(3)).size());
		assertEquals("Nenhuma Venda/Produto encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por produto")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void findByProductTest() {
		assertEquals(2, sellProductService.findByProductOrderByAmountSell(productService.findById(1)).size());
	}

	@Test
	@DisplayName("Teste busca por produto não encontrado")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void findByProductNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> sellProductService.findByProductOrderByAmountSell(productService.findById(3)).size());
		assertEquals("Nenhuma Venda/Produto encontrada", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca por quantidade entre")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void findByAmountSellBetweenTest() {
		assertEquals(2, sellProductService.findByAmountSellBetween(1, 2).size());
	}

	@Test
	@DisplayName("Teste busca por quantidade entre ( não encontrada )")
	@Sql("classpath:/resources/sqls/cidade.sql")
	@Sql("classpath:/resources/sqls/endereco.sql")
	@Sql("classpath:/resources/sqls/cliente.sql")
	@Sql("classpath:/resources/sqls/venda.sql")
	@Sql("classpath:/resources/sqls/categoria.sql")
	@Sql("classpath:/resources/sqls/produto.sql")
	@Sql("classpath:/resources/sqls/vendaProduto.sql")
	void findByAmountSellBetweenNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,
				() -> sellProductService.findByAmountSellBetween(5, 10).size());
		assertEquals("Nenhuma Venda/Produto encontrada", exception.getMessage());
	}

}
