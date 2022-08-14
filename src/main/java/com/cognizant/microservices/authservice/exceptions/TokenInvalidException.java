package com.cognizant.microservices.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenInvalidException extends Exception {
	public TokenInvalidException(String msg) {
		super(msg);
	}
}
