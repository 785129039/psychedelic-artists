package com.nex.web.spring.controller.web.browse;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Record;
import com.nex.domain.RecordComment;
import com.nex.domain.Type;
import com.nex.web.spring.controller.common.BrowseController;

import cz.tsystems.common.data.filter.Filter;

@Controller
@RequestMapping("/browse/preset/")
public class PresetBrowserController extends BrowseController<Record, RecordComment> {

	
	@Override
	public void configureFilter(Filter filter, HttpServletRequest request) {
		super.configureFilter(filter, request);
		filter.addDefaultCondition("type|Equal(Auto)", Type.PRESET.name());
	}

	@Override
	protected Class<Record> getEntityClass() {
		return Record.class;
	}

	@Override
	protected Class<RecordComment> getCommentClass() {
		return RecordComment.class;
	}
}
