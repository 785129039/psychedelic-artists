package com.nex.web.spring.controller.web;

import java.lang.reflect.Method;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.nex.domain.common.FileEntity;
import com.nex.utils.ReflectionUtils;
import com.nex.web.spring.controller.common.RejectErrorController;

public abstract class FileUploadController<T extends FileEntity> extends RejectErrorController {

	public abstract Class<T> getEntityClass();
	public abstract String uploadTemplate();
	@ModelAttribute("entity")
	public T loadEntity(@PathVariable("id") String id) {
		return findEntityById(id);
	}
	
	@RequestMapping("{id}")
	public String show() {
		return uploadTemplate();
	}
	@RequestMapping(value = "{id}", method=RequestMethod.POST)
	public String save(@ModelAttribute("entity") @Valid T entity,
			Errors errors, Model uiModel, HttpServletRequest request) {
		if(!errors.hasErrors()){
			entity.storeFile();
			entity.flush();
			request.setAttribute("success", true);
		} else {
			rejectFormErrors(RequestContextUtils.getLocale(request), errors);
		}
		return uploadTemplate();
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
	
}
