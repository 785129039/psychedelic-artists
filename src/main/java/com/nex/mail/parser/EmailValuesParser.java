package com.nex.mail.parser;

import java.util.Locale;

import org.springframework.context.MessageSource;

public interface EmailValuesParser extends Comparable<EmailValuesParser> {

	String[] getValues(String key, MessageSource mailSource, Locale locale);
	boolean canParse(String key, MessageSource mailSource, Locale locale);
	String getFormatableKey(String key, String[] values);
	public int getOrder();
}
