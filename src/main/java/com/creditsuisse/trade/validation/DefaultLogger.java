package com.creditsuisse.trade.validation;

import org.jboss.logging.Logger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

@MessageLogger(projectCode = "CS-")
public interface DefaultLogger {
	DefaultLogger LOGGER = Logger.getMessageLogger( DefaultLogger.class, DefaultLogger.class.getPackage().getName() );

	@LogMessage(level = Logger.Level.TRACE)
	@Message(id = 1, value = "Service invocation: [%s].")
	void httpTrace(String request);

	@LogMessage(level = Logger.Level.ERROR)
	@Message(id = 2, value = "Error [%s]: %s.")
	void genericError(String id, String stack);
}
