package com.creditsuisse.trade.validation.utils;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ProviderHelper {
	@Autowired
	private ApplicationContext ctx;

	public <T> T getBeanByType(final Class<T> clazz ) throws UnsupportedOperationException, BeansException {
		Map beansOfType = ctx.getBeansOfType( clazz );
		final int size = beansOfType.size();
		switch ( size ) {
			case 0:
				throw new UnsupportedOperationException("No bean found of type: " + clazz + ".");
			case 1:
				final String name = (String) beansOfType.keySet().iterator().next();
				return clazz.cast( ctx.getBean( name, clazz ) );
			default:
				throw new UnsupportedOperationException( "Ambiguous beans found of type: " + clazz + "." );
		}
	}
}
