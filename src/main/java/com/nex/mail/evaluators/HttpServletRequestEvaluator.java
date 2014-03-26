package com.nex.mail.evaluators;

import javax.servlet.http.HttpServletRequest;

import com.nex.mail.ValueFactory;

public class HttpServletRequestEvaluator extends AbstractEvaluator<HttpServletRequest> {
	
	private static final String SESSION = "session";
	private static final String REQUEST_PARAM = "requestParam";
	private static final String REQUEST_ATTR = "requestAttr";
	
	private final String[] startWith = {SESSION, REQUEST_PARAM, REQUEST_ATTR};
	
	@Override
	public Object getValueWithConvertedDefaultValue(String attributeKey, String defaultValue, Object[] data) {
		HttpServletRequest request = findObject(data);
		Object rootObject = null;
		String rootKey = null;
		for(String type: startWith) {
			if(!attributeKey.startsWith(type)) continue; 
			String[] keys = getObjectKeyAndPathKey(attributeKey, type);
			rootObject = getRootObject(request, type, keys[0]);
			rootKey = keys[1];
			break;
		}
		Object value = null;
		if(rootKey == null) {
			value = rootObject;
		} else {
			value = new ValueFactory(rootKey, getDefaultValueResolver()).getValue(rootObject, data);
		}
		return value == null? defaultValue: value;
	}
	
	private Object getRootObject(HttpServletRequest request, String type, String key) {
		if (type.equals(SESSION)) {
			return request.getSession().getAttribute(key);
		} else if (type.equals(REQUEST_ATTR)) {
			return request.getAttribute(key);
		} else if (type.equals(REQUEST_PARAM)) {
			return request.getParameter(key);
		}
		return null;
	}
	
	private String[] getObjectKeyAndPathKey(String key, String key2) {
		String[] splited = key.split(key2 + ".");
		int firstDot = splited[1].indexOf(".");
		if(firstDot < 0) return new String[] {splited[1], null};
		String objectKey = splited[1].substring(0, firstDot);
		String pathKey = splited[1].substring(firstDot+1);
		return new String[]{objectKey, pathKey};
	}
	
	@Override
	public boolean isMutable(String key) {
		for(String s: startWith) {
			if(key.startsWith(s)) return true; 
		}
		return false;
	}
	
}
