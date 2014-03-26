package com.nex.mail.parser;

import java.util.Locale;

import org.springframework.context.MessageSource;

public class NoValuesParser extends OrderedValuesParser {

	@Override
	public String[] getValues(String key,
			MessageSource mailSource, Locale locale) {
		return new String[0];
	}

	@Override
	public boolean canParse(String key, MessageSource mailSource,
			Locale locale) {
		return true;
	}

	@Override
	public String getFormatableKey(String key, String[] values) {
		return key;
	}

}
