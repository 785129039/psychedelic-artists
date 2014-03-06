package com.nex.web.freemarker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContext;

import com.nex.utils.ReflectionUtils;
import com.nex.utils.StringUtils;
import com.nex.utils.StringUtils.CompareType;
import com.nex.web.spring.controller.common.NestingRestfulCRUDController;

@Configurable
public class FreemarkerTemplateHelper {

	private Logger log = LoggerFactory.getLogger(FreemarkerTemplateHelper.class);
	@Autowired
	private ConversionService conversionService;
	@Autowired
	private MessageSource messageSource;
	
	private RequestContext requestContext;
	private Method _getRequestMethod;

	public FreemarkerTemplateHelper(RequestContext context) {
		this.requestContext = context;
		_getRequestMethod = ReflectionUtils.findMethod(RequestContext.class,
				"getRequest");
		ReflectionUtils.makeAccessible(_getRequestMethod);
	}

	public static String test() {
		return "test";
	}

	public String convertToString(Object value) {
		if (conversionService != null && value != null) {
			if (conversionService.canConvert(value.getClass(), String.class)) {
				return conversionService.convert(value, String.class);
			}
		}
		return String.valueOf(value);
	}

	public String getMessage(String code) {
		return getMessage(code, null);
	}

	public String getMessage(String code, Object[] args) {
		String defaultvalue = "???" + code + "???";
		if (this.messageSource != null) {
			// TODO ziskavat locale odkud se ma ziskavat
			String[] arguments = convertArray(args);
			return this.messageSource.getMessage(code, arguments, defaultvalue,
					getLocale());
		}
		return String.format(defaultvalue, args);
	}

	public String[] convertArray(Object... args) {
		if (args == null)
			return null;
		String[] formatedArguments = new String[args.length];
		for (int i = 0; i < formatedArguments.length; i++) {
			formatedArguments[i] = convertToString(args[i]);
		}
		return formatedArguments;
	}

	public FormModel createFormModel(String modelAttribute) {
		FormModel result = new FormModel(modelAttribute, this, requestContext);
		return result;
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) ReflectionUtils.invokeMethod(
				this._getRequestMethod, this.requestContext);
	}

	public String getRestMethod(Object commandObject) {
		HttpServletRequest request = getRequest();
		String _method = request.getMethod();
		Map<String, String> pathVariables = NestingRestfulCRUDController
				.getPathVariables();
		String idPart = pathVariables.get("id");
		if (idPart == null || "delete".equals(_method)) {
			return _method;
		}
		if ("new".equals(idPart)) {
			return RequestMethod.POST.name();
		} else {
			return RequestMethod.PUT.name();
		}
	}

	public Object resolveId(Object o) {
		if (o == null)
			return null;
		Object _id = null;
		try {
			Field f = getIdField(o);
			if (f == null) {
				throw new IllegalStateException(
						"Object "
								+ o.getClass()
								+ " must contain @Id annotation when using  <#macro renderButtons...");
			}
			_id = ReflectionUtils.reflectValueSimple(o, f.getName());
			if (_id == null) {
				throw new IllegalStateException(
						"Id value cannot be null when using <#macro renderButtons...");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return _id;
	}

	protected Field getIdField(Object o) {
		for (Field f : o.getClass().getDeclaredFields()) {
			if (f.getAnnotation(Id.class) != null) {
				return f;
			}
		}
		return null;
	}

	public boolean isUrlPart(String... url) {
		return isUrlPart(CompareType.ENDSWITH, url);
	}
	public boolean isUrlPart(String type, String... url) {
		return isUrlPart(CompareType.valueOf(type), url);
	}
	public boolean isUrlPart(CompareType type, String... url) {
		HttpServletRequest request = getRequest();
		String uri = request.getRequestURI().replaceFirst(request.getContextPath(), ""); 
		int pathParamIndex = uri.indexOf(';');
		if (pathParamIndex > 0) {
			uri = uri.substring(0, pathParamIndex);
		}
		int queryParamIndex = uri.indexOf('?');
		if (queryParamIndex > 0) {
			uri = uri.substring(0, queryParamIndex);
		}
		if ("".equals(request.getContextPath())) {
			return StringUtils.test(uri, CompareType.ENDSWITH, url);
		}
		return StringUtils.test(uri, type, url);
	}
	
	public void logInfo(Object object) {
		log.info(String.valueOf(object));
	}

	public void logError(Object object) {
		log.error(String.valueOf(object));
	}

	public Locale getLocale() {
		return this.requestContext.getLocale();
	}
}
