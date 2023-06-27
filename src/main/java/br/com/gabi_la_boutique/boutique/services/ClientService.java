package br.com.gabi_la_boutique.boutique.services;

import java.util.List;

import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.models.Client;

public interface ClientService {

	Client findById(Integer id);

	Client insert(Client client);

	List<Client> listAll();

	Client update(Client client);

	void delete(Integer id);

	List<Client> findByNameContainingIgnoreCase(String name);

	List<Client> findByEmailContainingIgnoreCase(String email);

	List<Client> findByAddressOrderByName(Address address);

}