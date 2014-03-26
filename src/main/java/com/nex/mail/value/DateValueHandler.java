package com.nex.mail.value;

import java.util.Date;

public class DateValueHandler implements DefaultValueHandler {

	@Override
	public Object convert(Object[] evaluatorData) {
		return new Date();
	}

}
