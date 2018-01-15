package com.creditsuisse.trade.validation.test.integration;

import java.util.Arrays;
import java.util.Date;

import com.creditsuisse.trade.validation.test.unit.BaseUnitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public abstract class BaseIntegrationTest extends BaseUnitTest {
	@Autowired
	protected TestRestTemplate restTemplate;

	@Autowired
	protected CacheManager cacheManager;

	protected Cache getFixerCache() {
		return cacheManager.getCache( "fixer" );
	}

	protected Object fixerCacheKey(Date date, String currency) {
		return Arrays.asList( date.getTime(), currency );
	}
}
