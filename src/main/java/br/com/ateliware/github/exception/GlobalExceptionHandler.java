package br.com.ateliware.github.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(NoSuchElementException.class)
	public void handleNoSuchElementException(NoSuchElementException e) {
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception e) {
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<String>("Um erro inesperado aconteceu, entre em contato com o suporte técnico", 
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}