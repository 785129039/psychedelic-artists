package com.nex.mail.parser;

import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

public class ExcludedEmailValuesParser extends OrderedValuesParser {

	@Override
	public String[] getValues(String key, MessageSource mailSource, Locale locale) {
		return getValuesKeys(key, mailSource, locale);
	}

	@Override
	public boolean canParse(String key, MessageSource mailSource, Locale locale) {
		String valuesInString = getValuesInString(key, mailSource, locale);
		return valuesInString != null && valuesInString.trim().length() > 1;
	}
	
	private String[] getValuesKeys(String key, MessageSource mailSource, Locale locale) {
		return getValuesInString(key, mailSource, locale).split(",");
	}

	private String getValuesInString(String key, MessageSource mailSource, Locale locale) {
		String valuesKey = key + ".values";
		try {
			String valuesInString = mailSource.getMessage(valuesKey, null, locale);
			return valuesInString;
		} catch (Exception e) {
			LoggerFactory.getLogger(ExcludedEmailValuesParser.class).debug("No values found for " + key + "...try chosing other values parser");
			return null;
		}
		
	}

	@Override
	public String getFormatableKey(String key, String[] values) {
		return key;
	}
	
}
