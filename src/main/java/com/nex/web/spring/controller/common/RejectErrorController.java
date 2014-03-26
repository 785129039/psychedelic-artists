package com.nex.web.spring.controller.common;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;

public class RejectErrorController {
	@Resource(name = "validationResourceMessageBundle")
	private ReloadableResourceBundleMessageSource validationMessageResourceBundle;
	
	protected void rejectFormErrors(Locale locale, Errors errors) {
		rejectAndTranslateError(locale, errors, "form.actions.save.error.message");
	}

	protected void rejectAndTranslateError(Locale locale, Errors errors, String code) {
		rejectAndTranslateError(locale, errors, code, null);
	}

	protected void addFieldError(Locale locale, Errors errors, String code, String fieldName) {
		this.addFieldError(locale, errors, code, fieldName, null);
	}
	
	protected void addFieldError(Locale locale, Errors errors, String code, String fieldName, String[] args) {
		String defaultMessage = code;
		if (this.validationMessageResourceBundle != null) {
			defaultMessage = this.validationMessageResourceBundle.getMessage(
					code, args,locale);
		}
		errors.rejectValue(fieldName, code, args, defaultMessage);
	}
	
	protected void rejectAndTranslateError(Locale locale, Errors errors, String code,
			Object[] args) {
		String defaultMessage = code;
		if (this.validationMessageResourceBundle != null) {
			defaultMessage = this.validationMessageResourceBundle.getMessage(
					code, args,locale);
		}
		errors.reject(code, defaultMessage);
	}
	public static Map<String, String> getPathVariables() {
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		@SuppressWarnings("unchecked")
		Map<String, String> uriTemplateVariables = (Map<String, String>) requestAttributes
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
						RequestAttributes.SCOPE_REQUEST);
		return uriTemplateVariables;
	}
	protected EntityManager getEntityManager(Object entity) {
		Method method = ReflectionUtils.findMethod(entity.getClass(),
				"entityManager");
		EntityManager em = (EntityManager) ReflectionUtils.invokeMethod(method,
				null);
		return em;
	}
}
