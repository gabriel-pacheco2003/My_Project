package br.com.gabi_la_boutique.boutique.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.Product;
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.models.SellProduct;
import br.com.gabi_la_boutique.boutique.repositories.SellProductRepository;
import br.com.gabi_la_boutique.boutique.services.SellProductService;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class SellProductServiceImpl implements SellProductService{

	@Autowired
	private SellProductRepository repository;
	
	private void validateSellProduct(SellProduct sellProduct) {
		if (sellProduct.getSell() == null){
			throw new IntegrityViolation("Venda inválida"); 
		}
		
		if (sellProduct.getProduct() == null){
			throw new IntegrityViolation("Produto inválido"); 
		}
		
		if (sellProduct.getAmountSell() == null || sellProduct.getAmountSell() <= 0){
			throw new IntegrityViolation("Quantidade inválida"); 
		}
		
		if (sellProduct.getAmountSell() > sellProduct.getProduct().getAmount()) {
			throw new IntegrityViolation("Estoque menor que a quantidade requerida");
		}
	}
	
	@Override
	public SellProduct findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Venda/Produto %s não encontrado".formatted(id)));
	}

	@Override
	public SellProduct insert(SellProduct sellProduct) {
		validateSellProduct(sellProduct);
		return repository.save(sellProduct);
	}

	@Override
	public List<SellProduct> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhuma Venda/Produto cadastrada");
		}
		return repository.findAll();
	}

	@Override
	public SellProduct update(SellProduct sellProduct) {
		validateSellProduct(sellProduct);
		return repository.save(sellProduct);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<SellProduct> findBySellOrderByAmountSell(Sell sell) {
		if (repository.findBySellOrderByAmountSell(sell).isEmpty()) {
			throw new ObjectNotFound("Nenhuma Venda/Produto encontrada");
		}
		return repository.findBySellOrderByAmountSell(sell);
	}

	@Override
	public List<SellProduct> findByProductOrderByAmountSell(Product product) {
		if (repository.findByProductOrderByAmountSell(product).isEmpty()) {
			throw new ObjectNotFound("Nenhuma Venda/Produto encontrada");
		}
		return repository.findByProductOrderByAmountSell(product);
	}

	@Override
	public List<SellProduct> findByAmountSellBetween(Integer amountSellIn, Integer amountSellFin) {
		if (repository.findByAmountSellBetween(amountSellIn, amountSellFin).isEmpty()) {
			throw new ObjectNotFound("Nenhuma Venda/Produto encontrada");
		}
		return repository.findByAmountSellBetween(amountSellIn, amountSellFin);
	}

}
