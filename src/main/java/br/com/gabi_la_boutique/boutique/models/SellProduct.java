package br.com.gabi_la_boutique.boutique.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "venda_produto")
public class SellProduct {
	
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_venda_produto")
	private Integer id;

	@ManyToOne
	private Sell sell;
	
	@ManyToOne
	private Product product;
	
	@Column(name = "qtde_venda_produto")
	private Integer amountSell;
	
}