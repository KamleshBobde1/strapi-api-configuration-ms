package com.entando.apiproxy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiConfigNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApiConfigNotFoundException() {
		super();
	}

	public ApiConfigNotFoundException(final String message) {
		super(message);
	}
}