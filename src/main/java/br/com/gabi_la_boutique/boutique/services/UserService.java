package br.com.gabi_la_boutique.boutique.services;

import java.util.List;

import br.com.gabi_la_boutique.boutique.models.User;


public interface UserService {

	User findById(Integer id);

	User insert(User user);

	List<User> listAll();

	User update(User user);

	void delete(Integer id);

	List<User> findByNameStartsWithIgnoreCase(String name);

}