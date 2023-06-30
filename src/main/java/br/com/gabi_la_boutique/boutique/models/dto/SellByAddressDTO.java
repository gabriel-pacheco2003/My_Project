package br.com.gabi_la_boutique.boutique.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SellByAddressDTO {

	private String address;
	
	private List<SellDTO> sells; 
	
}
