package br.com.gabi_la_boutique.boutique.services;

import java.time.LocalDate;
import java.util.List;

import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.models.Sell;

public interface SellService {

	Sell findById(Integer id);

	Sell insert(Sell sell);

	List<Sell> listAll();

	Sell update(Sell sell);

	void delete(Integer id);

	List<Sell> findByClient(Client client);
	
	List<Sell> findByDate(LocalDate date);
	
	List<Sell> findByDateBetween(LocalDate dateIn, LocalDate dateFin);
	
}
