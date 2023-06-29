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

import br.com.gabi_la_boutique.boutique.models.Product;
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.models.SellProduct;
import br.com.gabi_la_boutique.boutique.models.dto.SellProductDTO;
import br.com.gabi_la_boutique.boutique.services.ProductService;
import br.com.gabi_la_boutique.boutique.services.SellProductService;
import br.com.gabi_la_boutique.boutique.services.SellService;

@RestController
@RequestMapping("/sellProduct")
public class SellProductResource {

	@Autowired
	private SellProductService service;

	@Autowired
	private SellService sellService;

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<SellProductDTO> insert(@RequestBody SellProductDTO sellProductDTO) {
		return ResponseEntity.ok(
				service.insert(new SellProduct(sellProductDTO, productService.findById(sellProductDTO.getProductId()),
						sellService.findById(sellProductDTO.getSellId()))).toDTO());

	}

	@GetMapping("/{id}")
	public ResponseEntity<SellProductDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<SellProductDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map((sellProduct) -> sellProduct.toDTO()).toList());
	}

	@PutMapping("/{id}")
	public ResponseEntity<SellProductDTO> update(@PathVariable Integer id, @RequestBody SellProductDTO sellProductDTO) {
		SellProduct sellProduct = new SellProduct(sellProductDTO,
				productService.findById(sellProductDTO.getProductId()),
				sellService.findById(sellProductDTO.getSellId()));
		sellProduct.setId(id);
		return ResponseEntity.ok(service.update(sellProduct).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SellProduct> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/sell/{sell}")
	public ResponseEntity<List<SellProduct>> findBySellOrderByAmountSell(@PathVariable Sell sell) {
		return ResponseEntity.ok(service.findBySellOrderByAmountSell(sell));
	}

	@GetMapping("/product/{product}")
	public ResponseEntity<List<SellProduct>> findByProductOrderByAmountSell(@PathVariable Product product) {
		return ResponseEntity.ok(service.findByProductOrderByAmountSell(product));
	}

	@GetMapping("/inicialAmountSell/{amountSellIn}/finalAmountSell/{amountSellFin}")
	public ResponseEntity<List<SellProduct>> findByAmountSellBetween(@PathVariable Integer amountSellIn,
			@PathVariable Integer amountSellFin) {
		return ResponseEntity
				.ok(service.findByAmountSellBetween(amountSellIn, amountSellFin).stream().map((sell) -> sell).toList());
	}

}
