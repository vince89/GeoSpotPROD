package com.geospot.entities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

	public BadRequestException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
