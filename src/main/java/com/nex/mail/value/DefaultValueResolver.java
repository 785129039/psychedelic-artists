package com.nex.mail.value;

import java.util.Map;

import java.util.HashMap;

public class DefaultValueResolver {

	private Map<String, DefaultValueHandler> handlers = new HashMap<String, DefaultValueHandler>(); 
	
	public void setHandlers(Map<String, DefaultValueHandler> handlers) {
		this.handlers = handlers;
	}
	
	public Object resolveValue(Object value, Object[] evaluatorData) {
		DefaultValueHandler handler = handlers.get(value);
		if(handler != null) {
			return handler.convert(evaluatorData);
		}
		return value;
	}
}
