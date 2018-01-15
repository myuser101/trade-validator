package com.creditsuisse.trade.validation.utils;

import java.util.List;
import java.util.Map;

import com.creditsuisse.trade.validation.DefaultLogger;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.boot.actuate.trace.Trace;
import org.springframework.boot.actuate.trace.TraceRepository;

@Component
public class LoggingTrace implements TraceRepository {
	private final TraceRepository delegate = new InMemoryTraceRepository();

	@Override
	public List<Trace> findAll() {
		return delegate.findAll();
	}

	@Override
	public void add(Map<String, Object> traceInfo) {
		// Simplified. Push performance metrics to central services like Graphite.
		DefaultLogger.LOGGER.httpTrace( traceInfo.toString() );
		delegate.add( traceInfo );
	}
}
