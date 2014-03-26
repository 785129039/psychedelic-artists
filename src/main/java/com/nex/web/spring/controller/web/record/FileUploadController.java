package com.nex.web.spring.controller.web.record;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
	@ModelAttribute("_implclass")
	public String loadImplClass() {
		return prefix();
	}
	
	public abstract String prefix();
	
	@RequestMapping("{id}")
	public String show() {
		return uploadTemplate();
	}
	@RequestMapping(value = "{id}", method=RequestMethod.POST)
	public String save(@ModelAttribute("entity") @Valid T entity,
			Errors errors, Model uiModel, MultipartHttpServletRequest request) {
		entity.prepareFileForStore(request);
		additionalValidation(errors, entity, request);
		if(!errors.hasErrors()){
			try {
				entity.storeFile();
				entity.flush();
				request.setAttribute("_success", true);
			} catch (Exception e) {
				rejectAndTranslateError(RequestContextUtils.getLocale(request), errors, "form.actions.save.error.message.persist", new String[] {e.getMessage()});
			}
		} else {
			rejectFormErrors(RequestContextUtils.getLocale(request), errors);
		}
		return uploadTemplate();
	}
	public abstract void additionalValidation(Errors errors, T entity, HttpServletRequest request);
	
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
		//initialize ids
		entity.getGenreIds();
		return entity;
	}
	
	public class FileUploadForm {
		private Boolean success = Boolean.TRUE;
		private Errors errors;
		public Errors getErrors() {
			return errors;
		}
		public Boolean getSuccess() {
			return success;
		}
	}
	
}
