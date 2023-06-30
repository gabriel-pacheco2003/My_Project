package br.com.gabi_la_boutique.boutique.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static String dateToString(LocalDate date) {
		return dateFormatter.format(date);
	}

	public static LocalDate stringToDate(String dateString) {
		return LocalDate.parse(dateString, dateFormatter);
	}
}
