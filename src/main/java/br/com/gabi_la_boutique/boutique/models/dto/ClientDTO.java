package br.com.gabi_la_boutique.boutique.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientDTO {

	private Integer id;

	private String name;
	
	private String email;

	private Integer addressId;
	
	private String cityName;
	
}
