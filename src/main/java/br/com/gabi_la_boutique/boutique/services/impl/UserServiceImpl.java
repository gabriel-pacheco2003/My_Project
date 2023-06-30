package br.com.gabi_la_boutique.boutique.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.User;
import br.com.gabi_la_boutique.boutique.repositories.UserRepository;
import br.com.gabi_la_boutique.boutique.services.UserService;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	private void findByEmail(User user) {
		Optional<User> find = repository.findByEmail(user.getEmail());
		if (find.isPresent() && find.get().getId() != user.getId()) {
			throw new IntegrityViolation("Email já existente");
		}
	}

	@Override
	public User findById(Integer id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFound("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public User insert(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {
		List<User> lista = repository.findAll();
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum usuário cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public User update(User user) {
		findById(user.getId());
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);
	}

	@Override
	public List<User> findByNameStartsWithIgnoreCase(String name) {
		List<User> lista = repository.findByNameStartingWithIgnoreCase(name);
		if (lista.isEmpty()) {
			throw new ObjectNotFound("Nenhum nome de usuário inicia com %s".formatted(name));
		}
		return lista;
	}

}