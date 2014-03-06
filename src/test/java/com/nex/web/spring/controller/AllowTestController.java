package com.nex.web.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.security.permissions.aspect.Authorize;

@Controller
@RequestMapping("/allowtest/")
public class AllowTestController {

	@RequestMapping()
	public String test() {
		return "test/allow";
	}
	
	@Authorize("user")
	@RequestMapping("notallowed")
	public String notAllowed() {
		return "test/allow";
	}
	
}
