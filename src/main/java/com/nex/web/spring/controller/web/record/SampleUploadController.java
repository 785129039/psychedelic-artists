package com.nex.web.spring.controller.web.record;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.nex.domain.Record;
import com.nex.domain.RecordFileType;
import com.nex.utils.StringUtils;
import com.nex.utils.StringUtils.TypeConversion;

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

	@Override
	public void additionalValidation(Errors errors, Record entity,
			HttpServletRequest request) {
		MultipartFile file = entity.getFile();
		if(file == null) {
			rejectAndTranslateError(RequestContextUtils.getLocale(request), errors, "file.upload.not.null");
		} else {
			for(RecordFileType type: RecordFileType.values()) {
				String typeStr = type.name().toLowerCase();
				if(file.getOriginalFilename().endsWith(typeStr)) {
					return;
				}
			}
			String formatedTypes = StringUtils.getStringFromArray(RecordFileType.values(), ", ", new TypeConversion<RecordFileType>(){
				@Override
				public String convert(RecordFileType arg0) {
					return arg0.name().toLowerCase();
				}
			});
			addFieldError(RequestContextUtils.getLocale(request), errors, "file.upload.badType", "file", new String[] {formatedTypes});
		}
	}

}
