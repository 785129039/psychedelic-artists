package com.nex.web.spring.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nex.domain.Genre;
import com.nex.domain.Type;
import com.nex.domain.User;
import com.nex.domain.common.FileEntity;
import com.nex.security.permissions.aspect.Authorize;
import com.nex.security.permissions.checker.UserPermissionChecher;
import com.nex.utils.Requestutils;
import com.nex.web.spring.controller.common.NestingEntityRestfulCRUDController;

import cz.tsystems.common.data.filter.Filter;

public abstract class FileEntityController<T extends FileEntity> extends NestingEntityRestfulCRUDController<T> {
	
	@ModelAttribute("_implclass")
	public String loadImplClass() {
		return prefix();
	}
	public abstract String prefix();
	
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
		return "redirect:/web/my/upload/" + getType().name().toLowerCase() + "/" + entity.getId();
	}
	
	@Override
	protected T createNewEntity(HttpServletRequest request) {
		T s = super.createNewEntity(request);
		s.setUser(Requestutils.getLoggedUser());
		s.setType(getType());
		return s;
	}
	
	
	public abstract Type getType();
	
	@Override
	protected void checkPermission(T entity) {
		this.check(entity.getUser());
	}
	
	@Authorize(value={"ROLE_USER:loggedUser", "ROLE_ADMIN:loggedUser"}, checker = UserPermissionChecher.class)
	private void check(User user) {}
	
	@Override
	public void configureFilter(Filter filter, HttpServletRequest request) {
		filter.addDefaultCondition("user.id|Equal(Long)", Requestutils.getLoggedUser().getId().toString());
		filter.addConditionReplacement("name", "name|LRLike(String)");
		filter.addDefaultCondition("type|Equal(Auto)", getType().name());
	}
	
}
