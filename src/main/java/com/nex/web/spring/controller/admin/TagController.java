package com.nex.web.spring.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Tag;
import com.nex.web.spring.controller.common.NestingEntityRestfulCRUDController;

import cz.tsystems.common.data.filter.Filter;
import cz.tsystems.common.data.filter.SortDirection;

@Controller
@RequestMapping("/dials/tag/")
public class TagController extends NestingEntityRestfulCRUDController<Tag> {

	@Override
	public void init() {
		setEntityClass(Tag.class);
		setDefaultSortDirection(SortDirection.ASC);
		setDefaultSortProperty("id");
		setControllerURl("admin/tag/");
		setRedirectSaveToList(false);
	}

	@Override
	public void configureFilter(Filter filter, HttpServletRequest request) {
		filter.addConditionReplacement("name", "name|LRLike(String)");
	}
	
}
