package com.nex.web.spring.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nex.domain.Genre;
import com.nex.domain.User;
import com.nex.domain.common.FileEntity;
import com.nex.security.permissions.aspect.Authorize;
import com.nex.security.permissions.checker.UserPermissionChecher;
import com.nex.utils.Requestutils;
import com.nex.web.spring.controller.common.NestingEntityRestfulCRUDController;

public abstract class FileEntityController<T extends FileEntity> extends NestingEntityRestfulCRUDController<T> {
	
	@ModelAttribute("_implclass")
	public String loadImplClass() {
		return getEntityClass().getSimpleName();
	}
	
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
	protected T createNewEntity(HttpServletRequest request) {
		T s = super.createNewEntity(request);
		s.setUser(Requestutils.getLoggedUser());
		return s;
	}
	
	@Override
	protected void checkPermission(T entity) {
		this.check(entity.getUser());
	}
	@Authorize(value={"ROLE_USER:loggedUser", "ROLE_ADMIN:loggedUser"}, checker = UserPermissionChecher.class)
	private void check(User user) {}
}
