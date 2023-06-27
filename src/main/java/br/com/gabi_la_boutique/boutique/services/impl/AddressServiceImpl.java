package br.com.gabi_la_boutique.boutique.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.repositories.AddressRepository;
import br.com.gabi_la_boutique.boutique.services.AddressService;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository repository;

	@Override
	public Address findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Endereço %s não encontrado".formatted(id)));
	}

	@Override
	public Address insert(Address address) {
		return repository.save(address);
	}

	@Override
	public List<Address> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhum endereço cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public Address update(Address address) {
		return repository.save(address);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Address> findByStreetContainingIgnoreCase(String street) {
		if (repository.findByStreetContainingIgnoreCase(street).isEmpty()) {
			throw new ObjectNotFound("Nenhum endereço encontrado com a rua %s".formatted(street));
		}
		return repository.findByStreetContainingIgnoreCase(street);
	}

	@Override
	public List<Address> findByNeighborhoodContainingIgnoreCase(String neighborhood) {
		if (repository.findByNeighborhoodContainingIgnoreCase(neighborhood).isEmpty()) {
			throw new ObjectNotFound("Nenhum endereço encontrado com o bairro %s".formatted(neighborhood));
		}
		return repository.findByNeighborhoodContainingIgnoreCase(neighborhood);
	}

	@Override
	public List<Address> findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase(String street,
			String neighborhood) {
		if (repository.findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase(street, neighborhood)
				.isEmpty()) {
			throw new ObjectNotFound("Nenhum endereço encontrado com a rua %s e o bairro %s".formatted(street, neighborhood));
		}
		return repository.findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase(street, neighborhood);
	}

	@Override
	public List<Address> findByCity(City city) {
		if (repository.findByCity(city).isEmpty()) {
			throw new ObjectNotFound("Nenhum endereço encontrado com a cidade %s".formatted(city));
		}
		return repository.findByCity(city);
	}

}
