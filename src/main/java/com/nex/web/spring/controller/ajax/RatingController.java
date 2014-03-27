package com.nex.web.spring.controller.ajax;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rate/")
public class RatingController {
	@RequestMapping("{id}")
	public void rate(Integer value) {
		
	}
	
}
