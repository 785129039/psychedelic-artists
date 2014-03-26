package com.nex.web.spring.controller.common;

import java.io.IOException;

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
			request.setAttribute("title", 404);
			return "exceptions/error";
		case 403:
			request.setAttribute("title", 403);
			return "exceptions/error";
		default:
			request.setAttribute("title", 500);
			return "exceptions/error";
		}
	}
		
	@ExceptionHandler(NoAccessSecurityException.class)
	public void noPermissions(HttpServletResponse resp, HttpServletRequest request) throws IOException {
		resp.sendError(403);
	}
	@ExceptionHandler(PageNotFoundException.class)
	public void notFound(HttpServletResponse resp, HttpServletRequest request) throws IOException {
		resp.sendError(404);
	}
	@ExceptionHandler(Exception.class)
	public void serverError(HttpServletResponse resp, HttpServletRequest request, Exception e) throws IOException {
		resp.sendError(500);
		log.error("", e);
	}
	
}
