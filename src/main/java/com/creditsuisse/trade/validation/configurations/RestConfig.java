package com.creditsuisse.trade.validation.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
	@Value( "${rest.pool.timeout}" )
	private Integer poolTimeout;

	@Value( "${rest.read.timeout}" )
	private Integer readTimeout;

	@Value( "${rest.connection.timeout}" )
	private Integer connectionTimeout;

	@Bean
	public RestTemplate rest(RestTemplateBuilder builder) {
		// Enable connection pooling and persistent connection.
		final HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout( readTimeout );
		factory.setConnectionRequestTimeout( poolTimeout );
		factory.setConnectTimeout( connectionTimeout );
		return builder
				.requestFactory( new HttpComponentsClientHttpRequestFactory() )
				.build();
	}
}
