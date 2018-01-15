package com.creditsuisse.trade.validation.services;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FixerClient {
	// Simple date format is not thread-safe.
	private final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<SimpleDateFormat>() {
		@Override public SimpleDateFormat initialValue() {
			return new SimpleDateFormat( "yyyy-MM-dd" );
		}
	};

	@Value( "${fixer.url}" )
	private String endpoint;

	private final RestTemplate restTemplate;

	protected FixerClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@HystrixCommand(commandKey = "fixer")
	@Cacheable(value = "fixer", key="{#date.getTime(), #currency}")
	public Date getCurrencyDate(Date date, String currency) {
		final URI uri = URI.create( endpoint + "/" + dateFormatter.get().format( date ) + "?base=" + currency );
		final ResponseEntity<FixerResponse> response = restTemplate.getForEntity( uri, FixerResponse.class );
		return response.getBody().date;
	}

	private static class FixerResponse {
		private String base;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		private Date date;
	}
}
