package com.globant.project.utils;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.globant.project.exception.NoAuthorizationException;
import com.globant.project.pojo.ExceptionResponse;

@RestControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NoAuthorizationException.class)
	public ResponseEntity<ExceptionResponse> handleBadRangeOfDatesException(NoAuthorizationException ex) throws Exception{
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
	}

}
