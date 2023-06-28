package br.com.gabi_la_boutique.boutique.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.models.Sell;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer>{
	
	List<Sell> findByClient(Client client);
	
	List<Sell> findByDateOrderByDateDesc(LocalDate date);
	
	List<Sell> findByDateBetween(LocalDate dateIn, LocalDate dateFin);

	Sell findByDate(LocalDate date);
	
}
