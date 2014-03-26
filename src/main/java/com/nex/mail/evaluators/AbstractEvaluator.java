package com.nex.mail.evaluators;

import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.nex.mail.value.DefaultValueResolver;
import com.nex.utils.ReflectionUtils;


public abstract class AbstractEvaluator<T> implements Evaluator {
	
	
	private Logger log = LoggerFactory.getLogger(AbstractEvaluator.class);
	
	@Autowired
	protected MessageSource messageSource;
	
	private DefaultValueResolver defaultValueResolver;
	
	public void setDefaultValueResolver(DefaultValueResolver defaultValueResolver) {
		this.defaultValueResolver = defaultValueResolver;
	}
	public DefaultValueResolver getDefaultValueResolver() {
		return defaultValueResolver;
	}
	protected T findObject(Object[] data) {
		return (T) findObject(data, getGenericType());
	}
	
	@SuppressWarnings("unchecked")
	protected <E> E findObject(Object[] data, Class<E> type) {
		if(data != null)
		for(Object o: data) {
			if(isRequiredClass(o.getClass(), type)) {
				return (E) o;
			}
		}
		return null;
	}
	private boolean isRequiredClass(Class<?> dataClass, Class<?> requiredClass) {
		return ReflectionUtils.isChildOf(dataClass, requiredClass);
	}
	
	@Override
	public Object getValue(String attributeKey, String defaultValue, Object[] data) {
		if(defaultValueResolver != null) {
			return getValueWithConvertedDefaultValue(attributeKey, (String) defaultValueResolver.resolveValue(defaultValue, data), data);
		} else {
			log.warn("Evaluator " + getClass() + " has no " + DefaultValueResolver.class.getName());
		}
		
		return defaultValue;
	}
	
	public abstract Object getValueWithConvertedDefaultValue(String attributeKey, String defaultValue, Object[] data);
	
	@SuppressWarnings("unchecked")
	private Class<T> getGenericType() {
		ParameterizedType parameterizedType = (ParameterizedType)getClass()
                .getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	
}
