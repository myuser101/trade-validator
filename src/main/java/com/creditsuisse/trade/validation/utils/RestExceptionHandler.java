package com.creditsuisse.trade.validation.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.creditsuisse.trade.validation.DefaultLogger;
import com.creditsuisse.trade.validation.model.messages.ErrorMessage;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorMessage> handleException(Exception e) {
		final ErrorMessage error = new ErrorMessage( UUID.randomUUID().toString(), ZonedDateTime.now(), "E-001", "Generic error." );
		// Simplified. Logs should be asynchronously forwarded to central system, e.g. ElasticSearch.
		DefaultLogger.LOGGER.genericError( error.getId(), stackTraceToString( e ) );
		return new ResponseEntity<ErrorMessage>( error, HttpStatus.INTERNAL_SERVER_ERROR );
	}

	private String stackTraceToString(Exception e) {
		final StringWriter writer = new StringWriter();
		e.printStackTrace( new PrintWriter( writer ) );
		return writer.toString();
	}
}
