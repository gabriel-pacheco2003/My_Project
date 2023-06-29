package br.com.gabi_la_boutique.boutique.models;

import java.time.LocalDate;

import br.com.gabi_la_boutique.boutique.models.dto.SellDTO;
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
@Entity(name = "venda")
public class Sell {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_venda")
	private Integer id;

	@ManyToOne
	private Client client;

	@NotNull
	@Column(name = "data_venda")
	private LocalDate date;

	public Sell(SellDTO dto, Client client) {
		this(dto.getId(), client, DateUtils.stringToDate(dto.getDate()));
	}

	public SellDTO toDTO() {
		return new SellDTO(id, DateUtils.dateToString(date), client.getId(), client.getName());
	}

}
