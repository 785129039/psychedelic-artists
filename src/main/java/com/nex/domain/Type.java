package com.nex.domain;

public enum Type {

	SAMPLE, PRESET;
	private String code;
	private Type() {
		this.code = "Record.type." + name().toLowerCase();
	}
	
	public String getCode() {
		return code;
	}
}
