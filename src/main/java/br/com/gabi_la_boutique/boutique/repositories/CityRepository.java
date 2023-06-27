package br.com.gabi_la_boutique.boutique.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabi_la_boutique.boutique.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

	List<City> findByNameContainingIgnoreCase(String name);
	
	City findByName(String city);
	
}
