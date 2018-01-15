package com.creditsuisse.trade.validation.configurations;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {
	@Autowired
	private ResourceLoader resourceLoader;

	@Bean
	public CacheManager cacheManager() {
		final EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		try {
			ehCacheCacheManager.setCacheManager( ehcacheCacheManager().getObject() );
		}
		catch ( Exception e ) {
			throw new RuntimeException( "Failed to instantiate cache manager: " + e.getMessage() + ".", e );
		}
		return ehCacheCacheManager;
	}

	@Bean
	public FactoryBean<net.sf.ehcache.CacheManager> ehcacheCacheManager() {
		final EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
		bean.setShared( true );
		bean.setConfigLocation( resourceLoader.getResource( "classpath:ehcache.xml" ) );
		return bean;
	}
}
