package br.com.gabi_la_boutique.boutique.services;

import java.util.List;

import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.models.City;

public interface AddressService {

	Address findById(Integer id);

	Address insert(Address address);

	List<Address> listAll();

	Address update(Address address);

	void delete(Integer id);

	List<Address> findByStreetContainingIgnoreCase(String street);

	List<Address> findByNeighborhoodContainingIgnoreCase(String neighborhood);
	
	List<Address> findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase(String street, String neighborhood);

	List<Address> findByCity(City city);
	
}
