package com.nex.web.spring.controller.common;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

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
	
}
