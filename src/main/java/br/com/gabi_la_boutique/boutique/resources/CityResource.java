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

import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.services.CityService;

@RestController
@RequestMapping("/city")
public class CityResource {

	@Autowired
	private CityService service;

	@PostMapping
	public ResponseEntity<City> insert(@RequestBody City city) {
		return ResponseEntity.ok(service.insert(city));
	}

	@GetMapping("/{id}")
	public ResponseEntity<City> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<City>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((city) -> city).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<City> update(@PathVariable Integer id, @RequestBody City city) {
		city.setId(id);
		return ResponseEntity.ok(service.update(city));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<City> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<City>> findByNameContainingIgnoreCase(@PathVariable String name) {
		return ResponseEntity.ok(service.findByNameContainingIgnoreCase(name).stream().map((city) -> city).toList());
	}
}