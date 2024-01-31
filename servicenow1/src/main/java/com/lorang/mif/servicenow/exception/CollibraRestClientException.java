package com.lorang.mif.servicenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class  CollibraRestClientException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollibraRestClientException(String message) {
		super(message);
	}

	public CollibraRestClientException(String message, Throwable cause) {
		super(message, cause);
	}
}