package com.nex.web.spring.controller.web.record;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Record;
import com.nex.domain.Type;
import com.nex.web.spring.controller.web.FileEntityController;

import cz.tsystems.common.data.filter.SortDirection;

@Controller
@RequestMapping("/my/detail/sample/")
public class SampleController extends FileEntityController<Record> {

	@Override
	public void init() {
		setEntityClass(Record.class);
		setDefaultSortDirection(SortDirection.ASC);
		setDefaultSortProperty("id");
		setControllerURl("web/record/");
		setRedirectSaveToList(false);
	}

	@Override
	public Type getType() {
		return Type.SAMPLE;
	}

	@Override
	public String prefix() {
		return "Sample";
	}
	
}
