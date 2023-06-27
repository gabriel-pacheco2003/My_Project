package br.com.gabi_la_boutique.boutique.resources.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StandardError {

	private LocalDateTime time;
	private Integer status;
	private String error;
	private String url;

}
