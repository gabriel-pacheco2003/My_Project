package br.com.gabi_la_boutique.boutique.services;

import java.util.List;

import br.com.gabi_la_boutique.boutique.models.Product;
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.models.SellProduct;

public interface SellProductService {

	SellProduct findById(Integer id);

	SellProduct insert(SellProduct sellProduct);

	List<SellProduct> listAll();

	SellProduct update(SellProduct sellProduct);

	void delete(Integer id);
	
	List<SellProduct> findBySellOrderByAmountSell(Sell sell);
	
	List<SellProduct> findByProductOrderByAmountSell(Product product);
	
	List<SellProduct> findByAmountSellBetween(Integer amountSellIn, Integer amountSellFin);
	
}
