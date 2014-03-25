package com.nex.web.spring.controller.web.record;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Record;
import com.nex.web.spring.controller.web.FileUploadController;

@Controller
@RequestMapping("/my/upload/sample/")
public class SampleUploadController extends FileUploadController<Record> {

	@Override
	public Class<Record> getEntityClass() {
		return Record.class;
	}

	@Override
	public String uploadTemplate() {
		return "web/record/upload";
	}

	@Override
	public String prefix() {
		return "Sample";
	}

}
