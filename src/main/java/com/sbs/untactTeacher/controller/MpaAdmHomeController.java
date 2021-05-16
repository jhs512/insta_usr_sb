package com.sbs.untactTeacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MpaAdmHomeController {
	@RequestMapping("/mpaAdm")
	public String showMainRoot() {
		return "redirect:/mpaAdm/home/main";
	}
	
	@RequestMapping("/mpaAdm/home/main")
	public String showMain() {
		return "mpaAdm/home/main";
	}
}