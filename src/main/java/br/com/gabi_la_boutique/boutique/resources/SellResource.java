package br.com.gabi_la_boutique.boutique.resources;

import java.time.LocalDate;
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
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.services.ClientService;
import br.com.gabi_la_boutique.boutique.services.SellService;

@RestController
@RequestMapping("/sell")
public class SellResource {

	@Autowired
	private SellService service;

	@Autowired
	private ClientService clientService;

	@PostMapping
	public ResponseEntity<Sell> insert(@RequestBody Sell sell) {
		clientService.findById(sell.getClient().getId());
		return ResponseEntity.ok(service.insert(sell));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Sell> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<Sell>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((sell) -> sell).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Sell> update(@PathVariable Integer id, @RequestBody Sell sell) {
		sell.setId(id);
		clientService.findById(sell.getClient().getId());
		return ResponseEntity.ok(service.update(sell));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Sell> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/client/{clientId}")
	public ResponseEntity<List<Sell>> findByClient(@PathVariable City clientId) {
		return ResponseEntity.ok(service.findByClient(clientService.findById(clientId.getId())));
	}
	
	@GetMapping("/date/{date}")
	public ResponseEntity<List<Sell>> findByDateOrderByDateDesc(@PathVariable LocalDate date) {
		return ResponseEntity.ok(service.findByDateOrderByDateDesc(date).stream().map((sell) -> sell).toList());
	}
	
	@GetMapping("/inicialDate/{dateIn}/finalDate/{dateFin}")
	public ResponseEntity<List<Sell>> findByDateBetween(@PathVariable LocalDate dateIn, @PathVariable LocalDate dateFin) {
		return ResponseEntity.ok(service.findByDateBetween(dateIn, dateFin).stream().map((sell) -> sell).toList());
	}

}
