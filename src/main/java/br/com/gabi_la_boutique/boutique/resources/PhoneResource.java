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

import br.com.gabi_la_boutique.boutique.models.Client;
import br.com.gabi_la_boutique.boutique.models.Phone;
import br.com.gabi_la_boutique.boutique.services.ClientService;
import br.com.gabi_la_boutique.boutique.services.PhoneService;

@RestController
@RequestMapping("/phone")
public class PhoneResource {

	@Autowired
	private PhoneService service;

	@Autowired
	private ClientService clientService;

	@PostMapping
	public ResponseEntity<Phone> insert(@RequestBody Phone phone) {
		clientService.findById(phone.getClient().getId());
		return ResponseEntity.ok(service.insert(phone));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Phone> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Phone>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((phone) -> phone).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Phone> update(@PathVariable Integer id, @RequestBody Phone phone) {
		phone.setId(id);
		clientService.findById(phone.getClient().getId());
		return ResponseEntity.ok(service.update(phone));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Phone> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/number/{number}")
	public ResponseEntity<List<Phone>> findByNumberOrderByClient(@PathVariable String number) {
		return ResponseEntity.ok(service.findByNumberOrderByClient(number).stream().map((phone) -> phone).toList());
	}

	@GetMapping("/client/{clientId}")
	public ResponseEntity<List<Phone>> findByClient(@PathVariable Client clientId) {
		return ResponseEntity.ok(service.findByClient(clientService.findById(clientId.getId())));
	}

}
