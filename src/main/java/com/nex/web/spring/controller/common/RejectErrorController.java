package com.nex.web.spring.controller.common;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Errors;

public class RejectErrorController {
	@Resource(name = "validationResourceMessageBundle")
	private ReloadableResourceBundleMessageSource validationMessageResourceBundle;
	
	protected void rejectFormErrors(Locale locale, Errors errors) {
		rejectAndTranslateError(locale, errors, "form.actions.save.error.message");
	}

	protected void rejectAndTranslateError(Locale locale, Errors errors, String code) {
		rejectAndTranslateError(locale, errors, code, null);
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
