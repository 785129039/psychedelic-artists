package com.nex.web.freemarker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.support.BindStatus;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.nex.domain.localizedfield.LocalizedFieldAdapter;
import com.nex.utils.ReflectionUtils;

import cz.tsystems.common.data.filter.FilteredList;
import cz.tsystems.common.data.filter.IFilter;

public class FormModel {
	public static String keyForPageNumber = "page";
	public static String keyForSortBy = "sortBy";
	public static String keyForSortOrder = "sortOrder";
	
	private Logger log = LoggerFactory.getLogger(FormModel.class);
	private FreemarkerTemplateHelper templateHelper;
	private RequestContext requestContext;
	private BindStatus bindStatus;
	private Object formObject;
	private String commandName;
	public FormModel(String commandName, FreemarkerTemplateHelper templateHelper,
			RequestContext requestContext) {
		super();
		this.templateHelper = templateHelper;
		this.requestContext = requestContext;
		formObject = templateHelper.getRequest().getAttribute(commandName);           
	    if (formObject==null) {
	      formObject = requestContext.getModel().get(commandName);
	    }
	    this.commandName = commandName;
	    bindStatus = requestContext.getBindStatus(commandName);  
	    Assert.notNull(formObject, "Model attribute " + commandName + " not found in model");
	}
	  public String[] getFormErrorsAsString() {

		      return bindStatus.getErrorMessages();

	  }
	  
	  public String[] readCustomValidationMessages(String... codes) {
		  List<String> additionalErrors = new ArrayList<String>();
		  for(String code: codes) {
			  Errors err = getRequestContext().getErrors(commandName);
			  FieldError error = err.getFieldError(code);
			  if(error == null) continue;
			  String message = error.getDefaultMessage();
			  additionalErrors.add(message);
		  }
		  return additionalErrors.toArray(new String[additionalErrors.size()]);
	  }
	  
	  public String readLocalizedValue(String path, String locale) {
		  Object _adapter = readValue(path);
		  String _value = null;
		  if(_adapter instanceof LocalizedFieldAdapter) {
			  LocalizedFieldAdapter lfa = (LocalizedFieldAdapter) _adapter;
			  _value = lfa.getAsMap().get(locale);

		  }
		  return _value == null? "":_value;
	  }
	  
	public Object readValue(String path) {
		Object value = getValueFromFilter(path);
		if(StringUtils.isEmpty(value)) {
			value = getObjectValue(path);
		}
		return value == null?"":value;
	}
	
	public String readFormatedValue(String path) {
		return templateHelper.convertToString(readValue(path));
	}
	
	public Object getObjectValue(String path) {
		Object mappedObject = ReflectionUtils.reflectValue(this.formObject, path);
		Object mappedValue = getNestedValue(mappedObject);
		return mappedValue;
	}
	
	private Object getNestedValue(Object o) {
		if(o == null) return null;
		//TODO udelat podporu pro list (napriklad kdyz bude multiple selected)
		Class<?> objectClass = o.getClass();
		//zjistime jestli se jedna o entitu, pokud ano najdeme a vratime ID
		if(objectClass.getAnnotation(Entity.class) != null) {
			//podpora pro spravne nastavovani option v selectu
				Field f = templateHelper.getIdField(o);
				if(f != null) {
					return ReflectionUtils.reflectValueSimple(o, f.getName());
				}
				log.warn("No field in class " + objectClass + " has annotation @Id.");
		}
		return o;
	}
	
	
	
	public String getValueFromFilter(String path) {
		String value = null;
		if(formObject instanceof FilteredList) {
			IFilter filter = ((FilteredList<?>)formObject).getFilter();
			value = filter.getConditions().get(path);
			if(value == null) {
				value = resolvePageOrSort(path, filter);
			}
		}
		return value;
	}
	
	
	private String resolvePageOrSort(String path, IFilter filter) {
		if(keyForPageNumber.equals(path)) {
			return String.valueOf(filter.getPage().getCurrentPageNumber());
		} else if(keyForSortBy.equals(path)) {
			return filter.getSortBy().getColumn();
		} else if(keyForSortOrder.equals(path)) {
			return filter.getSortBy().getSortDirection().name().toLowerCase();
		}
		return null;
	}
	
	public String getAction() {
		//TODO get action from current url
		return "";
	}
	
	public Object getFormObject() {
		return formObject;
	}
	
	public FormFieldModel getFieldModel(String path) {
		return new FormFieldModel(path, commandName, this, RequestContextUtils.getLocale(templateHelper.getRequest()));
	}
	
	protected RequestContext getRequestContext() {
		return requestContext;
	}
}
