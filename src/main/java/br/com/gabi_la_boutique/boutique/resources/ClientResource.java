package br.com.gabi_la_boutique.boutique.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import br.com.gabi_la_boutique.boutique.models.dto.ClientDTO;
import br.com.gabi_la_boutique.boutique.services.AddressService;
import br.com.gabi_la_boutique.boutique.services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientResource {

	@Autowired
	private ClientService service;

	@Autowired
	private AddressService addressService;

	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO clientDTO) {
		return ResponseEntity
				.ok(service.insert(new Client(clientDTO, addressService.findById(clientDTO.getAddressId()))).toDTO());

	}

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<ClientDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((client) -> client.toDTO()).toList());
	}

	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Integer id, @RequestBody ClientDTO clientDTO) {
		Client client = new Client(clientDTO, addressService.findById(clientDTO.getAddressId()));
		client.setId(id);
		return ResponseEntity.ok(service.update(client).toDTO());
	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Client> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Client>> findByNameContainingIgnoreCase(@PathVariable String name) {
		return ResponseEntity
				.ok(service.findByNameContainingIgnoreCase(name).stream().map((client) -> client).toList());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/email/{email}")
	public ResponseEntity<List<Client>> findByEmailContainingIgnoreCase(@PathVariable String email) {
		return ResponseEntity
				.ok(service.findByEmailContainingIgnoreCase(email).stream().map((client) -> client).toList());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/address/{addressId}")
	public ResponseEntity<List<Client>> findByAddressOrderByName(@PathVariable Address addressId) {
		return ResponseEntity.ok(service.findByAddressOrderByName(addressService.findById(addressId.getId())));
	}
}
