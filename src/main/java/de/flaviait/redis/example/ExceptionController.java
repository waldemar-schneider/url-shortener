package de.flaviait.redis.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(value = NotFoundException.class)
	public HttpEntity<String> handleNotFoundException(NotFoundException ex) {
		LOGGER.debug("short url not found: {}", ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public HttpEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		LOGGER.debug("illegal argument was given: {}", ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public HttpEntity<String> handleUnexpectedException(RuntimeException ex) {
		LOGGER.error("unexpected exception: {}", ex.getMessage());
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
