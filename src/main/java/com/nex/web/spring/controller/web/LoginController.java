package com.nex.web.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class LoginController {
 
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
 
	}
	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(Model model) {
		model.addAttribute("error", "true");
		return "login";
 
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		return "login";
 
	}
}
