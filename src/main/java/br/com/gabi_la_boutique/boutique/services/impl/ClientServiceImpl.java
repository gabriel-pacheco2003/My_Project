package br.com.gabi_la_boutique.boutique.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.repositories.ClientRepository;
import br.com.gabi_la_boutique.boutique.services.ClientService;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository repository;

	private void validateClient(Client client) {
		if (client.getName() == null) {
			throw new IntegrityViolation("Nome inválido");
		}

		if (client.getEmail() == null) {
			throw new IntegrityViolation("Email inválido");
		}

		if (client.getName() == null) {
			throw new IntegrityViolation("Nome inválido");
		}

		if (client.getAddress() == null) {
			throw new IntegrityViolation("Endereço inválido");
		}
		
		Client find = repository.findByEmail(client.getEmail());
		if (find != null && find.getId() != client.getId()) {
			throw new IntegrityViolation("Email já existente");
		}
	}

	@Override
	public Client findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Cliente %s não encontrado".formatted(id)));
	}

	@Override
	public Client insert(Client client) {
		validateClient(client);
		return repository.save(client);
	}

	@Override
	public List<Client> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public Client update(Client client) {
		validateClient(client);
		return repository.save(client);
	}

	@Override
	public void delete(Integer id) {
		repository.save(findById(id));
	}

	@Override
	public List<Client> findByNameContainingIgnoreCase(String name) {
		if (repository.findByNameContainingIgnoreCase(name).isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente encontrado com o nome %s".formatted(name));
		}
		return repository.findByNameContainingIgnoreCase(name);
	}

	@Override
	public List<Client> findByEmailContainingIgnoreCase(String email) {
		if (repository.findByEmailContainingIgnoreCase(email).isEmpty()) {
			throw new ObjectNotFound("Email inválido");
		}
		return repository.findByEmailContainingIgnoreCase(email);
	}

	@Override
	public List<Client> findByAddressOrderByName(Address address) {
		if (repository.findByAddressOrderByName(address).isEmpty()) {
			throw new ObjectNotFound("Nenhum cliente encontrado com o endereço %s".formatted(address));
		}
		return repository.findByAddressOrderByName(address);
	}

}
