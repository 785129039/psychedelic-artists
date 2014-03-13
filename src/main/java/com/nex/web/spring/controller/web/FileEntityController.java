package com.nex.web.spring.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

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
		entity.storeFile();
	}
	
	@Override
	protected void onEntityChanged(T newEntity, T oldEntity) {
		newEntity.prepareForSave();
		newEntity.storeFile();
	}
	@Override
	protected T createNewEntity(HttpServletRequest request) {
		T s = super.createNewEntity(request);
		s.setUser(Requestutils.getLoggedUser());
		return s;
	}
}
