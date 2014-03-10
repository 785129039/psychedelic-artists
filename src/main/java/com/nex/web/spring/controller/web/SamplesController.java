package com.nex.web.spring.controller.web;

import com.nex.domain.Sample;
import com.nex.web.spring.controller.common.NestingEntityRestfulCRUDController;

public class SamplesController extends NestingEntityRestfulCRUDController<Sample> {

	@Override
	public void init() {
		setEntityClass(Sample.class);
		setDefaultSortProperty("id");
	}

	
	
}
