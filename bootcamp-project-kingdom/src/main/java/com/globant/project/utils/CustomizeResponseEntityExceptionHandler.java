package com.globant.project.utils;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.globant.project.exceptions.IllegalEnumException;
import com.globant.project.exceptions.RelatedObjectNotFoundException;
import com.globant.project.pojo.ExceptionResponse;


@RestControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RelatedObjectNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleRelatedObjectNotFoundException(RelatedObjectNotFoundException ex) throws Exception{
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}
	
	@ExceptionHandler(IllegalEnumException.class)
	public ResponseEntity<ExceptionResponse> handleIllegalEnumException(IllegalEnumException ex) throws Exception{
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}
	
}
