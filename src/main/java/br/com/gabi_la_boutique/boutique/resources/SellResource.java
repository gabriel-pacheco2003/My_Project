package br.com.gabi_la_boutique.boutique.resources;

import java.time.LocalDate;
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

import br.com.gabi_la_boutique.boutique.models.City;
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.models.dto.SellDTO;
import br.com.gabi_la_boutique.boutique.services.ClientService;
import br.com.gabi_la_boutique.boutique.services.SellService;
import br.com.gabi_la_boutique.boutique.utils.DateUtils;

@RestController
@RequestMapping("/sell")
public class SellResource {

	@Autowired
	private SellService service;

	@Autowired
	private ClientService clientService;

	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<SellDTO> insert(@RequestBody SellDTO sellDTO) {
		return ResponseEntity
				.ok(service.insert(new Sell(sellDTO, clientService.findById(sellDTO.getClientId()))).toDTO());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<SellDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<SellDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((sell) -> sell.toDTO()).toList());
	}

	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<SellDTO> update(@PathVariable Integer id, @RequestBody SellDTO sellDTO) {
		Sell sell = new Sell(sellDTO, clientService.findById(sellDTO.getClientId()));
		sell.setId(id);
		return ResponseEntity.ok(service.update(sell).toDTO());
	}

	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Sell> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/client/{clientId}")
	public ResponseEntity<List<Sell>> findByClient(@PathVariable City clientId) {
		return ResponseEntity.ok(service.findByClient(clientService.findById(clientId.getId())));
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/date/{date}")
	public ResponseEntity<List<Sell>> findByDateOrderByDateDesc(@PathVariable String date) {
		return ResponseEntity.ok(service.findByDateOrderByDateDesc(DateUtils.stringToDate(date)).stream().map((sell) -> sell).toList());
	}

	@Secured({"ROLE_USER"})
	@GetMapping("/inicialDate/{dateIn}/finalDate/{dateFin}")
	public ResponseEntity<List<Sell>> findByDateBetween(@PathVariable LocalDate dateIn,
			@PathVariable LocalDate dateFin) {
		return ResponseEntity.ok(service.findByDateBetween(dateIn, dateFin).stream().map((sell) -> sell).toList());
	}

}
