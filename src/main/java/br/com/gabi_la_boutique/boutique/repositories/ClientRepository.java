package br.com.gabi_la_boutique.boutique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	List<Client> findByNameContainingIgnoreCase(String name);

	List<Client> findByEmailContainingIgnoreCase(String email);

	List<Client> findByAddressOrderByName(Address address);
	
	Client findByEmail(String email);
}
