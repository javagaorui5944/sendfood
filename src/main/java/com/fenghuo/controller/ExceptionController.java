package com.fenghuo.controller;

import org.springframework.ejb.access.EjbAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ex")
public class ExceptionController {

	@RequestMapping("/exTest")
	public void ex() throws Exception{
		throw new EjbAccessException("ha");
	}
}
