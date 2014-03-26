package com.nex.mail;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nex.mail.value.DefaultValueResolver;
import com.nex.utils.ReflectionUtils;

public class ValueFactory {

	private Logger log = LoggerFactory.getLogger(ValueFactory.class);
	private ValueGenerator valueGenerator;
	private DefaultValueResolver defaultValueResolver;
	public ValueFactory(String key, DefaultValueResolver resolver) {
		super();
		this.valueGenerator = new ValueGenerator();
		this.valueGenerator.parse(key);
		this.defaultValueResolver = resolver;
	}

	public Object getValue(Object data, Object[] evaluatorData) {
		String[] keys = valueGenerator.getKey().split("\\.");
		Object value = null;
		for (String k : keys) {
			ArrayInfo ai = getArrayInfo(k);
			if (ai != null) {
				value = getObjectFromArray(ai, ReflectionUtils.reflectValueSimple(data, ai.arrayName));
			} else {
				value = ReflectionUtils.reflectValueSimple(data, k);
			}
			data = value;
		}
		if(value == null) {
			value = valueGenerator.getDefaultValue();
			if(defaultValueResolver == null) {
				log.warn("DefaultValueResolver is null!!");
			} else {
				value = defaultValueResolver.resolveValue(value, evaluatorData);
			}
		}
		return value;
	}
	
	private Object getObjectFromArray(ArrayInfo ai, Object array) {
		if (array instanceof List) {
			List<?> list = (List<?>) array;
			return list.get(ai.getArrayIndex());
		} else if (array instanceof Array) {
			Object[] objArray = (Object[]) array;
			return objArray[ai.getArrayIndex()];
		} else if (array instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) array;
			return map.get(ai.key);
		}
		throw new RuntimeException("Field " + ai.arrayName
				+ " is not type of Array, List or Map.");
	}

	private ArrayInfo getArrayInfo(String key) {
		String[] array = tryParseArrayMark(key);
		if (array != null && array.length == 2) {
			ArrayInfo ai = new ArrayInfo();
			ai.arrayName = array[0];
			ai.key = array[1];
			return ai;
		}
		return null;
	}

	private String[] tryParseArrayMark(String key) {
		String[] test = key.split("\\[|\\]");
		return test;
	}

	public ValueGenerator getValueGenerator() {
		return valueGenerator;
	}
	
	private class ArrayInfo {
		public String arrayName;
		public String key;

		public Integer getArrayIndex() {
			return Integer.valueOf(key);
		}

	}
	
}
