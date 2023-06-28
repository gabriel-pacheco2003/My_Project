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
import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.services.AddressService;
import br.com.gabi_la_boutique.boutique.services.CityService;

@RestController
@RequestMapping("/address")
public class AddressResource {

	@Autowired
	private AddressService service;

	@Autowired
	private CityService cityService;

	@PostMapping
	public ResponseEntity<Address> insert(@RequestBody Address address) {
		cityService.findById(address.getCity().getId());
		return ResponseEntity.ok(service.insert(address));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Address> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Address>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((address) -> address).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Address> update(@PathVariable Integer id, @RequestBody Address address) {
		address.setId(id);
		cityService.findById(address.getCity().getId());
		return ResponseEntity.ok(service.update(address));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Address> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/street/{street}")
	public ResponseEntity<List<Address>> findByStreetContainingIgnoreCase(@PathVariable String street) {
		return ResponseEntity
				.ok(service.findByStreetContainingIgnoreCase(street).stream().map((address) -> address).toList());
	}

	@GetMapping("/neighborhood/{neighborhood}")
	public ResponseEntity<List<Address>> findByNeighborhoodContainingIgnoreCase(@PathVariable String neighborhood) {
		return ResponseEntity.ok(service.findByNeighborhoodContainingIgnoreCase(neighborhood).stream()
				.map((address) -> address).toList());
	}

	@GetMapping("/street/{street}/neighborhood/{neighborhood}")
	public ResponseEntity<List<Address>> findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase(
			@PathVariable String street, @PathVariable String neighborhood) {
		return ResponseEntity
				.ok(service.findByStreetContainingIgnoreCaseAndNeighborhoodContainingIgnoreCase(street, neighborhood)
						.stream().map((address) -> address).toList());
	}

	@GetMapping("/city/{cityId}")
	public ResponseEntity<List<Address>> findByCity(@PathVariable City cityId) {
		return ResponseEntity.ok(service.findByCity(cityService.findById(cityId.getId())));
	}

}
