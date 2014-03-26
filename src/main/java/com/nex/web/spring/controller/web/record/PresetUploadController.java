package com.nex.web.spring.controller.web.record;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.Record;

@Controller
@RequestMapping("/my/upload/preset/")
public class PresetUploadController extends FileUploadController<Record> {

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
		return "Preset";
	}

	@Override
	public void additionalValidation(Errors errors, Record entity,
			HttpServletRequest request) {
		
		
	}
}
