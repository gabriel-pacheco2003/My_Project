package br.com.gabi_la_boutique.boutique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabi_la_boutique.boutique.models.Product;
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.models.SellProduct;

@Repository
public interface SellProductRepository extends JpaRepository<SellProduct, Integer>{
	
	List<SellProduct> findBySellOrderByAmountSell(Sell sell);
	
	List<SellProduct> findByProductOrderByAmountSell(Product product);
	
	List<SellProduct> findByAmountSellBetween(Integer amountSellIn, Integer amountSellFin);
	
	SellProduct findByAmountSell(Integer amountSell);

}
