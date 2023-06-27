package br.com.gabi_la_boutique.boutique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.models.City;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

	List<Address> findByStreetContainingIgnoreCase(String street);

	List<Address> findByNeighborhoodContainingIgnoreCase(String neighborhood);
	
	List<Address> findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase(String street, String neighborhood);

	List<Address> findByCity(City city);

}
