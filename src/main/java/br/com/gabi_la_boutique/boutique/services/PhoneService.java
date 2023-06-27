package br.com.gabi_la_boutique.boutique.services;

import java.util.List;

import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.models.Phone;

public interface PhoneService {

	Phone findById(Integer id);

	Phone insert(Phone phone);

	List<Phone> listAll();

	Phone update(Phone phone);

	void delete(Integer id);
	
	List<Phone> findByNumber(String number);

	List<Phone> findByClient(Client client);
	
}
