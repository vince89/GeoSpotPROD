package com.geospot.entities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
