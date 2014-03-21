package com.nex.web.spring.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Preset;
import com.nex.utils.Requestutils;

import cz.tsystems.common.data.filter.Filter;
import cz.tsystems.common.data.filter.SortDirection;

@Controller
@RequestMapping("/preset/")
public class PresetController extends FileEntityController<Preset> {

	@Override
	public void init() {
		setEntityClass(Preset.class);
		setDefaultSortDirection(SortDirection.ASC);
		setDefaultSortProperty("id");
		setControllerURl("web/record/");
		setRedirectSaveToList(false);
	}
	@Override
	public void configureFilter(Filter filter, HttpServletRequest request) {
		filter.addConditionReplacement("name", "name|LRLike(String)");
		filter.addDefaultCondition("user.id|Equal(Long)", Requestutils.getLoggedUser().getId().toString());
	}
	
}
