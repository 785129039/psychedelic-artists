package com.nex.web.spring.controller.web.browse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Preset;
import com.nex.domain.PresetComment;
import com.nex.web.spring.controller.common.BrowseController;

import cz.tsystems.common.data.filter.Filter;
import cz.tsystems.common.data.filter.SortDirection;

@Controller
@RequestMapping("/browse/preset/")
public class PresetBrowserController extends BrowseController<Preset, PresetComment> {

	
	@Override
	public void configureFilter(Filter filter, HttpServletRequest request) {
		filter.addConditionReplacement("name", "name|LRLike(String)");
	}

	@Override
	protected String controllerUrl() {
		return "web/browse/";
	}

	@Override
	protected Class<Preset> getEntityClass() {
		return Preset.class;
	}

	@Override
	protected String defaultSortColumn() {
		return "modifiedOn";
	}

	@Override
	protected SortDirection defaultSortDirection() {
		return SortDirection.DESC;
	}

	@Override
	protected Class<PresetComment> getCommentClass() {
		return PresetComment.class;
	}

	@Override
	protected String targetField() {
		return "preset";
	}
	
}
