package br.com.gabi_la_boutique.boutique.services;

import java.util.List;

import br.com.gabi_la_boutique.boutique.models.City;

public interface CityService {
	
	City findById(Integer id);

	City insert(City city);

	List<City> listAll();

	City update(City city);

	void delete(Integer id);
	
	List<City> findByNameContainingIgnoreCase(String name);

}
