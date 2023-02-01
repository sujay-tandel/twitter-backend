package com.clone.twitter.model;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorResponse {

	private Integer statusCode;
	private Date timestamp;
	private String message;
	private String description;

	public ErrorResponse(Integer statusCode, Date timestamp, String message, String description) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}
}
