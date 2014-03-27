package com.nex.web.spring.controller.web.browse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Record;
import com.nex.domain.RecordComment;
import com.nex.web.spring.controller.common.BrowseController;

@Controller
@RequestMapping("/browse/overview/")
public class OverviewBrowserController extends BrowseController<Record, RecordComment> {
	
	@Override
	protected Class<Record> getEntityClass() {
		return Record.class;
	}

	@Override
	public String getListTemplate() {
		return "overview";
	}
	
	@Override
	protected Class<RecordComment> getCommentClass() {
		return RecordComment.class;
	}
	@Override
	public Record findEntityById(String id) {
		Record r = super.findEntityById(id);
		if(r==null) return null;
		r.getGenreIds();
		r.setChangeModification(false);
		return r;
	}
}
