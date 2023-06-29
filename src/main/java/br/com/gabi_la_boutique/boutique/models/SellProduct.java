package br.com.gabi_la_boutique.boutique.models;

import br.com.gabi_la_boutique.boutique.models.dto.SellProductDTO;
import br.com.gabi_la_boutique.boutique.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	@Column(name = "qtde_venda_produto")
	private Integer amountSell;

	public SellProduct(SellProductDTO dto, Product product, Sell sell) {
		this(dto.getId(), sell, product, dto.getAmount());
	}

	public SellProductDTO toDTO() {
		return new SellProductDTO(id, product.getId(), product.getName(), product.getPrice(), sell.getId(),
				DateUtils.dateToString(sell.getDate()), sell.getClient().getName(), amountSell);
	}

}
