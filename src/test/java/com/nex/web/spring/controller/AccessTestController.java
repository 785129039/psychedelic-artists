package com.nex.web.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accesstest/")
public class AccessTestController {
	
	@RequestMapping
	public String test() {
		return "test/access";
	}
	
}
