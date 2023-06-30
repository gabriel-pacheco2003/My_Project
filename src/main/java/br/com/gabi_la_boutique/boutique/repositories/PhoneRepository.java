package br.com.gabi_la_boutique.boutique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.models.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer>{

	List<Phone> findByNumberContainingOrderByClient(String number);

	List<Phone> findByClient(Client client);
	
	Phone findByNumberContaining(String number);
	
}
