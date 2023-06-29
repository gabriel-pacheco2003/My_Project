package br.com.gabi_la_boutique.boutique.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PhoneDTO {

	private Integer Id;
	
	private String number;
	
	private Integer clientId;
	
	private String clientName;
	
}
