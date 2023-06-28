package br.com.gabi_la_boutique.boutique.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.repositories.SellRepository;
import br.com.gabi_la_boutique.boutique.services.SellService;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@Service
public class SellServiceImpl implements SellService {

	@Autowired
	private SellRepository repository;

	private void validateSell(Sell sell) {
		if (sell.getClient() == null) {
			throw new IntegrityViolation("Cliente inválido");
		}

		Sell find = repository.findByDate(sell.getDate());
		if (find != null && find.getDate().isBefore(LocalDate.now())) {
			throw new IntegrityViolation("Data inválida");
		}
	}

	@Override
	public Sell findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Venda %s não encontrada".formatted(id)));
	}

	@Override
	public Sell insert(Sell sell) {
		validateSell(sell);
		return repository.save(sell);
	}

	@Override
	public List<Sell> listAll() {
		if (repository.findAll().isEmpty()) {
			throw new ObjectNotFound("Nenhuma venda cadastrada");
		}
		return repository.findAll();
	}

	@Override
	public Sell update(Sell sell) {
		validateSell(sell);
		return repository.save(sell);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Sell> findByClient(Client client) {
		if (repository.findByClient(client).isEmpty()) {
			throw new ObjectNotFound("Nenhuma venda encontrada com o cliente %s".formatted(client));
		}
		return repository.findByClient(client);
	}

	@Override
	public List<Sell> findByDateOrderByDateDesc(LocalDate date) {
		if (repository.findByDateOrderByDateDesc(date).isEmpty()) {
			throw new ObjectNotFound("Nenhuma venda encontrada com a data: %s".formatted(date));
		}
		return repository.findByDateOrderByDateDesc(date);
	}

	@Override
	public List<Sell> findByDateBetween(LocalDate dateIn, LocalDate dateFin) {
		if (repository.findByDateBetween(dateIn, dateFin).isEmpty()) {
			throw new ObjectNotFound("Nenhuma venda encontrada");
		}
		return repository.findByDateBetween(dateIn, dateFin);
	}

}
