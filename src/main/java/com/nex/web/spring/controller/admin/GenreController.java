package com.nex.web.spring.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Genre;
import com.nex.web.spring.controller.common.NestingEntityRestfulCRUDController;

import cz.tsystems.common.data.filter.Filter;
import cz.tsystems.common.data.filter.SortDirection;

@Controller
@RequestMapping("/genre/")
public class GenreController extends NestingEntityRestfulCRUDController<Genre> {

	@Override
	public void init() {
		setEntityClass(Genre.class);
		setDefaultSortDirection(SortDirection.ASC);
		setDefaultSortProperty("id");
		setControllerURl("admin/genre/");
		setRedirectSaveToList(false);
	}

	@Override
	public void configureFilter(Filter filter, HttpServletRequest request) {
		filter.addConditionReplacement("name", "name|LRLike(String)");
	}
	
}
