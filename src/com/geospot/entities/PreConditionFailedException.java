package com.geospot.entities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PreConditionFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
