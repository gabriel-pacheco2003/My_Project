package br.com.gabi_la_boutique.boutique.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.repositories.CityRepository;
import br.com.gabi_la_boutique.boutique.services.CityService;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository repository;

	private void validateName(City city) {
		City find = repository.findByName(city.getName());
		if (find != null && find.getId() != city.getId()) {
			throw new IntegrityViolation("Cidade já existente");
		}
	}

	@Override
	public City findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Cidade %s não encontrada".formatted(id)));
	}

	@Override
	public City insert(City city) {
		validateName(city);
		return repository.save(city);
	}

	@Override
	public List<City> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhuma cidade cadastrada");
		}
		return repository.findAll();
	}

	@Override
	public City update(City city) {
		validateName(city);
		return repository.save(city);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<City> findByNameContainingIgnoreCase(String name) {
		if (repository.findByNameContainingIgnoreCase(name).isEmpty()) {
			throw new ObjectNotFound("Nenhuma cidade encontrada com o nome %s".formatted(name));
		}
		return repository.findByNameContainingIgnoreCase(name);
	}

}
