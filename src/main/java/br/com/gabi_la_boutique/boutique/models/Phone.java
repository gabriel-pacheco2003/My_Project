package br.com.gabi_la_boutique.boutique.models;

import br.com.gabi_la_boutique.boutique.models.dto.PhoneDTO;
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
@Entity(name = "telefone")
public class Phone {
	
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_telefone")
	private Integer id;
	
	@NotNull
	@Column(name = "numero_telefone", unique = true)
	private String number;
	
	@ManyToOne
	private Client client;

	public String formatNumber(String number) {
		String digits = number.replaceAll("[^0-9]", "");
		String areaCode = digits.substring(0, 2);
		String prefix = digits.substring(2, 7);
		String suffix = digits.substring(7);
		String formatNumber = String.format("(%s) %s-%s".formatted(areaCode, prefix, suffix));
		this.number = formatNumber;
		return this.number;
	}
	
	public Phone(PhoneDTO dto, Client client) {
		this(dto.getId(), dto.getNumber(), client);
	}
	
	public PhoneDTO toDTO() {
		return new PhoneDTO(id, number, client.getId(), client.getName());
	}

	
}
