package com.nex.web.spring.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nex.annotation.Logger;
import com.nex.web.spring.controller.common.ExceptionController;

@Controller
@RequestMapping("/error/")
@ControllerAdvice
@Logger
public class AdminExceptionController extends ExceptionController {}
