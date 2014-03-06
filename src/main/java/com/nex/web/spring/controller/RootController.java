package com.nex.web.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.domain.localized.Type;

@Controller
@RequestMapping("/")
public class RootController {

	@RequestMapping
	private String index() {
		return "index";
	}
}
