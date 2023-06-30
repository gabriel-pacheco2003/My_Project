package br.com.gabi_la_boutique.boutique.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.models.Phone;
import br.com.gabi_la_boutique.boutique.repositories.PhoneRepository;
import br.com.gabi_la_boutique.boutique.services.PhoneService;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class PhoneServiceImpl implements PhoneService {

	@Autowired
	private PhoneRepository repository;

	private void validatePhone(Phone phone) {
		if (phone.getClient() == null) {
			throw new IntegrityViolation("Cliente inválido");
		}
		
		if (phone.getNumber().replaceAll("[^0-9]", "").length() != 11) {
			throw new IntegrityViolation("Número de telefone com tamanho incorreto");
		}
		
		Phone find = repository.findByNumberContaining(phone.getNumber());
		if (find != null && find.getId() != phone.getId()) {
			throw new IntegrityViolation("Número de telefone inválido");
		}
	}

	@Override
	public Phone findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Número de telefone %s não encontrado".formatted(id)));
	}

	@Override
	public Phone insert(Phone phone) {
		phone.formatNumber(phone.getNumber());
		validatePhone(phone);
		return repository.save(phone);
	}

	@Override
	public List<Phone> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhum número de telefone cadastrado");
		}
		return repository.findAll();
	}

	@Override
	public Phone update(Phone phone) {
		phone.formatNumber(phone.getNumber());
		validatePhone(phone);
		return repository.save(phone);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Phone> findByNumberContainingOrderByClient(String number) {
		if (repository.findByNumberContainingOrderByClient(number).isEmpty()) {
			throw new ObjectNotFound("Nenhum número de telefone encontrado com o número %s".formatted(number));
		}
		return repository.findByNumberContainingOrderByClient(number);
	}

	@Override
	public List<Phone> findByClient(Client client) {
		if (repository.findByClient(client).isEmpty()) {
			throw new ObjectNotFound("Nenhum número de telefone encontrado");
		}
		return repository.findByClient(client);
	}

}
