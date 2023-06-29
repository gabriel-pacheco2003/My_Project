package br.com.gabi_la_boutique.boutique.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SellProductDTO {

	private Integer id;

	private Integer productId;

	private String productName;

	private Double productPrice;

	private Integer sellId;

	private String sellDate;
	
	private String clientName;

	private Integer amount;

}
