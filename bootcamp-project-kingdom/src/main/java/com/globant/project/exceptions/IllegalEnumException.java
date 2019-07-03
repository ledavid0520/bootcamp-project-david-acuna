package com.globant.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IllegalEnumException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public IllegalEnumException(String message) {
		super(message);
	}
}
