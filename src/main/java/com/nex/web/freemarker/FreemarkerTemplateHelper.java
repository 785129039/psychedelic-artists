package com.nex.web.freemarker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
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

	public String convertToString(Object value) {
		if(value != null && value instanceof List) {
			return value.toString();
		}
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
		String _method = getFormMethod();
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
	
	public String getFormMethod() {
		HttpServletRequest request = getRequest();
		String _method = request.getMethod();
		return _method;
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
	
	public Object evaluate(String field, Object source) {
		return ReflectionUtils.reflectValue(source, field);
	}
	public String evaluateAsString(String field, Object source) {
		Object result = ReflectionUtils.reflectValue(source, field);
		return convertToString(result);
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
	public String getUrl(String path, String context) {
		return requestContext.getContextPath() + context + path;
	}
	public Boolean isNull(Object item) {
		if(item == null || item.toString().trim().equals("") || item.toString().trim().equals("_NULL_")) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public Boolean isOptionSelected(String value, Object source) {
		try {
			if(source instanceof List) {
				@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>)source;
				for(Object o: list) {
					Object id = ReflectionUtils.reflectValue(o, "id");
					if(value.equals(String.valueOf(id))) {
						return Boolean.TRUE;
					}
				}
			}
			Object id = ReflectionUtils.reflectValue(source, "id");
			value.equals(id!=null?String.valueOf(id):null);
		} catch (Exception e) {
			log.error("", e);
		}
		
		return Boolean.FALSE;
	}
}
