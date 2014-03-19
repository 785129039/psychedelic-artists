package com.nex.web.spring.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nex.domain.Genre;
import com.nex.domain.common.FileEntity;
import com.nex.utils.Requestutils;
import com.nex.web.spring.controller.common.NestingEntityRestfulCRUDController;

public abstract class FileEntityController<T extends FileEntity> extends NestingEntityRestfulCRUDController<T> {
	@ModelAttribute("genres")
	public List<Genre> loadGenres() {
		return Genre.findGenresByPublished(Boolean.TRUE).getResultList();
	}
	
	@Override
	protected void preEntityCreated(T entity) {
		entity.prepareForSave();
	}
	
	@Override
	protected void onEntityCreated(T entity) {
	}
	
	@Override
	protected void onEntityChanged(T newEntity, T oldEntity) {
		newEntity.prepareForSave();
	}
	@Override
	public String _create(@ModelAttribute("entity") @Valid T entity, Errors errors, Model uiModel,
			HttpServletRequest request) {
		String _res = super._create(entity, errors, uiModel, request);
		if(errors.hasErrors()) {
			return _res;
		}
		return "redirect:/web/upload/"+getEntityClass().getSimpleName() + "/"+entity.getId();
	}
	@Override
	public String _update(@ModelAttribute("entity") @Valid T entity, Errors errors, Model uiModel,
			HttpServletRequest request) {
		String _res = super._update(entity, errors, uiModel, request);
		if(errors.hasErrors()) {
			return _res;
		}
		return "redirect:/web/upload/"+getEntityClass().getSimpleName().toLowerCase() + "/"+entity.getId();
	}
	@Override
	protected T createNewEntity(HttpServletRequest request) {
		T s = super.createNewEntity(request);
		s.setUser(Requestutils.getLoggedUser());
		return s;
	}
}
