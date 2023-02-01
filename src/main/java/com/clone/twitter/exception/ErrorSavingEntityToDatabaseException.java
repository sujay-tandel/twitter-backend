package com.clone.twitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ErrorSavingEntityToDatabaseException  extends RuntimeException {

	public ErrorSavingEntityToDatabaseException() {
		super();
	}

	public ErrorSavingEntityToDatabaseException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ErrorSavingEntityToDatabaseException(final String message) {
		super(message);
	}

	public ErrorSavingEntityToDatabaseException(final Throwable cause) {
		super(cause);
	}
}
