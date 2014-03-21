package com.nex.web.spring.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.annotation.Logger;
import com.nex.exceptions.PageNotFoundException;
import com.nex.security.permissions.NoAccessSecurityException;
import com.nex.web.spring.view.FreemarkerView;

@Controller
@RequestMapping("/error/")
@ControllerAdvice
@Logger
public class ExceptionController {
	
	@RequestMapping("{errorCode}")
	public String displayServerError(@PathVariable("errorCode") int errorcode, HttpServletRequest request, HttpServletResponse resp) {
		request.setAttribute(FreemarkerView.IS_ERORR, true);
		switch (errorcode) {
		case 404:
			return notFound(resp, request);
		case 403:
			return noPermissions(resp, request);
		default:
			return serverError(resp, request);
		}
	}
		
	@ExceptionHandler(NoAccessSecurityException.class)
	public String noPermissions(HttpServletResponse resp, HttpServletRequest request) {
		request.setAttribute("title", 403);
		return "exceptions/error";
	}
	@ExceptionHandler(PageNotFoundException.class)
	public String notFound(HttpServletResponse resp, HttpServletRequest request) {
		request.setAttribute("title", 404);
		return "exceptions/error";
	}
	@ExceptionHandler(Exception.class)
	public String serverError(HttpServletResponse resp, HttpServletRequest request) {
		request.setAttribute("title", 500);
		return "exceptions/error";
	}
	
}
