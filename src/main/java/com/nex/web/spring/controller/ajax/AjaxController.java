package com.nex.web.spring.controller.ajax;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nex.domain.Tag;

@Controller
@RequestMapping("/load/")
public class AjaxController {

	@RequestMapping("tags")
	private @ResponseBody List<Tag> loadTags(@RequestParam(value="text", required=false) String searchString) throws JsonGenerationException, JsonMappingException, IOException {
		return Tag.likeTags(searchString);
	}
	
}
