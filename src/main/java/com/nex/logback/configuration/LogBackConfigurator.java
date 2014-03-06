package com.nex.logback.configuration;

import java.io.InputStream;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

public class LogBackConfigurator {
	
	private String logBackFile;
	
	public void configure() {
		
		// assume SLF4J is bound to logback in the current environment
	    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
	    
	    try {
	      LoggerFactory.getLogger(LogBackConfigurator.class).info("Start configurating logback with file " + logBackFile);
	      InputStream is = LogBackConfigurator.class.getClassLoader().getResourceAsStream("/" + logBackFile); 
	      JoranConfigurator configurator = new JoranConfigurator();
	      configurator.setContext(context);
	      // Call context.reset() to clear any previous configuration, e.g. default 
	      // configuration. For multi-step configuration, omit calling context.reset().
	      context.reset(); 
	     
	      configurator.doConfigure(is);
	    } catch (Exception je) {
	    	LoggerFactory.getLogger(LogBackConfigurator.class).error("", je);
	    }
	}
	
	public void setLogBackFile(String logBackFile) {
		this.logBackFile = logBackFile;
	}
	
	public String getLogBackFile() {
		return logBackFile;
	}
}
