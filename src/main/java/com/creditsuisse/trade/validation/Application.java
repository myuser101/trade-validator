package com.creditsuisse.trade.validation;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class Application {
	@PostConstruct
	public void started() {
		TimeZone.setDefault( TimeZone.getTimeZone( "UTC" ) );
	}

	public static void main(String[] args) {
		SpringApplication.run( Application.class, args );
	}
}
