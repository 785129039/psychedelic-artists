package com.nex.mail;

public class ValueGenerator {

	
	private String key;
	private String defaultValue = "";
	
	
	public void parse(String key) {
		String[] keys = key.split(":");
		this.key = keys[0];
		if(keys.length > 1)
		this.defaultValue = keys[1];
	}
	
	public String getKey() {
		return key;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
}
