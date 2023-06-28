package br.com.gabi_la_boutique.boutique.utils;

import br.com.gabi_la_boutique.boutique.models.Phone;
import br.com.gabi_la_boutique.boutique.services.exceptions.IntegrityViolation;

public class NumberUtils {

	private String validatePhone(Phone phone) {

		if (phone.getNumber().replaceAll("[^0-9]", "").length() != 11) {
			throw new IntegrityViolation("Número de telefone inválido");
		}
		String areaCode = phone.getNumber().replaceAll("[^0-9]", "").substring(0, 2);
		String prefix = phone.getNumber().replaceAll("[^0-9]", "").substring(2, 7);
		String suffix = phone.getNumber().replaceAll("[^0-9]", "").substring(7);

		String formattedNumber = String.format("(%s) %s-%s", areaCode, prefix, suffix);

		return formattedNumber;
	}
}
