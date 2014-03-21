package com.nex.web.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Sample;

@Controller
@RequestMapping("/upload/sample/")
public class SampleUploadController extends FileUploadController<Sample> {

	@Override
	public Class<Sample> getEntityClass() {
		return Sample.class;
	}

	@Override
	public String uploadTemplate() {
		return "web/record/upload";
	}

}
