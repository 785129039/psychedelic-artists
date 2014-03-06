package com.nex.web.spring.controller.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import com.nex.utils.ReflectionUtils;

public abstract class NestingRestfulCRUDController<T> extends RejectErrorController {
	private static final Logger log = LoggerFactory.getLogger(NestingRestfulCRUDController.class);

	private Class<T> entityClass;
	
	@ModelAttribute("entities")
	public List<T> loadDeleteEntities(@RequestParam(value="_multid",required=false) List<String> ids){
		if(ids!=null && !ids.isEmpty()) {
			List<T> entities = new ArrayList<T>();
			for(String id: ids) {
				entities.add(findEntityById(id));
			}
			return entities;
		}
		return Collections.emptyList();
	}
	@ModelAttribute("entity")
	public T loadEntity(HttpServletRequest request) {
		String id = null;
		try {

			Map<String, String> uriTemplateVariables = getPathVariables();
			if (uriTemplateVariables != null) {
				id = uriTemplateVariables.get("id");
				if (id != null && !"none".equals(id) && !"all".equals(id)
						&& !"filtered".equals(id)) {// pokud je ID namapovano
					try {
						request.setAttribute("id", id);
						if ("new".equals(id)) {
							return createNewEntity(request);
						} else {
							T entity = findEntityWithId(request, id);
							return entity;
						}
					} catch (Exception e) {
						throw new RuntimeException("", e);
					}

				} else {
					return null;
				}
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
	}
	
	protected T createNewEntity(HttpServletRequest request) {
		try {
			return (T) getEntityClass().newInstance();
		} catch (Exception e) {
			log.error("Create of new entity failed!", e);
			throw new RuntimeException("Create of new entity failed!", e);
		}
	}

	@SuppressWarnings("unchecked")
	protected T findEntityWithId(HttpServletRequest request, String id) {
		return findEntityById(id);
	}

	public T findEntityById(String id) {
		Class<T> entityClass = getEntityClass();
		String findByIdMethodName = "find" + entityClass.getSimpleName();
		Method findByIdMethod = ReflectionUtils.findMethod(entityClass,
				findByIdMethodName, Long.class);
		T entity = (T) ReflectionUtils.invokeMethod(findByIdMethod, null,
				new Long(id));
		if (entity == null) {
			throw new EntityNotFoundException("Entity "
					+ entityClass.getSimpleName() + " with id=" + id
					+ " not found.");
		}
		return entity;
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
	
	
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
}

