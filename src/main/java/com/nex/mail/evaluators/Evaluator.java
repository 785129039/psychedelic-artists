package com.nex.mail.evaluators;

public interface Evaluator {

	
	Object getValue(String attributeKey, String defaultValue, Object[] data);
	boolean isMutable(String key);
}
