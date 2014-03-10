package com.nex.web.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {

	@RequestMapping
	private String index() {
		return "index";
	}
}
