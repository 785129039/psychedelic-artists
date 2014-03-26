package com.nex.mail.evaluators;

import javax.servlet.http.HttpServletRequest;

import com.nex.utils.RequestUtils;


public class ApplicationContextURLEvaluator extends AbstractEvaluator<HttpServletRequest> {

	

	@Override
	public boolean isMutable(String key) {
		return false;
	}

	@Override
	public Object getValueWithConvertedDefaultValue(String attributeKey,
			String defaultValue, Object[] data) {
		return RequestUtils.getBaseUrl(findObject(data));
	}
	
}
