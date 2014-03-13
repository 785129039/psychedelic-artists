package com.nex.web.spring.controller.web.browse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Sample;
import com.nex.domain.SampleComment;
import com.nex.web.spring.controller.common.BrowseController;

import cz.tsystems.common.data.filter.Filter;
import cz.tsystems.common.data.filter.SortDirection;

@Controller
@RequestMapping("/browse/sample/")
public class SampleBrowserController extends BrowseController<Sample, SampleComment> {

	
	@Override
	public void configureFilter(Filter filter, HttpServletRequest request) {
		filter.addConditionReplacement("name", "name|LRLike(String)");
	}

	@Override
	protected String controllerUrl() {
		return "web/browse/sample/";
	}

	@Override
	protected Class<Sample> getEntityClass() {
		return Sample.class;
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
	protected Class<SampleComment> getCommentClass() {
		return SampleComment.class;
	}

	@Override
	protected String targetField() {
		return "sample";
	}
	
}
