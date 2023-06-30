package br.com.gabi_la_boutique.boutique.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabi_la_boutique.boutique.models.Address;
import br.com.gabi_la_boutique.boutique.models.Sell;
import br.com.gabi_la_boutique.boutique.models.dto.SellByAddressDTO;
import br.com.gabi_la_boutique.boutique.models.dto.SellDTO;
import br.com.gabi_la_boutique.boutique.services.AddressService;
import br.com.gabi_la_boutique.boutique.services.ClientService;
import br.com.gabi_la_boutique.boutique.services.SellService;
import br.com.gabi_la_boutique.boutique.services.exceptions.ObjectNotFound;

@RestController
@RequestMapping("/report")
public class ReportResource {
	
	@Autowired
	SellService sellService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	AddressService addressService;
	
	@GetMapping("/sells-by-address/{addressId}")
	public ResponseEntity<SellByAddressDTO> findSellByClientAndCity(@PathVariable Integer addressId){
		
		Address address = addressService.findById(addressId);
		
		List<SellDTO> sellDTOs = clientService.findByAddressOrderByName(address).stream()
		        .flatMap(client -> {
		            try {
		                return sellService.findByClient(client).stream();
		            } catch (ObjectNotFound e) {
		                return Stream.empty();
		            }
		        })
		        .filter(sell -> sell.getClient() != null )
		        .map(Sell::toDTO)
		        .toList();
		
		return ResponseEntity.ok(new SellByAddressDTO(address.getCity().getName(),sellDTOs));

	}

}
