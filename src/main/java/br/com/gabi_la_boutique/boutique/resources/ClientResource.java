package br.com.gabi_la_boutique.boutique.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.services.AddressService;
import br.com.gabi_la_boutique.boutique.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientResource {

	@Autowired
	private ClientService service;

	@Autowired
	private AddressService addressService;

	@PostMapping
	public ResponseEntity<Client> insert(@RequestBody Client client) {
		addressService.findById(client.getAddress().getId());
		return ResponseEntity.ok(service.insert(client));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Client>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((client) -> client).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@PathVariable Integer id, @RequestBody Client client) {
		client.setId(id);
		addressService.findById(client.getAddress().getId());
		return ResponseEntity.ok(service.update(client));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Client> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Client>> findByNameContainingIgnoreCase(@PathVariable String name) {
		return ResponseEntity
				.ok(service.findByNameContainingIgnoreCase(name).stream().map((client) -> client).toList());
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<List<Client>> findByEmailContainingIgnoreCase(@PathVariable String email) {
		return ResponseEntity
				.ok(service.findByEmailContainingIgnoreCase(email).stream().map((client) -> client).toList());
	}

	@GetMapping("/address/{addressId}")
	public ResponseEntity<List<Client>> findByAddressOrderByName(@PathVariable Address addressId) {
		return ResponseEntity.ok(service.findByAddressOrderByName(addressService.findById(addressId.getId())));
	}
}
