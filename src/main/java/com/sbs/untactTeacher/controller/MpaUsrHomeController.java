package com.sbs.untactTeacher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MpaUsrHomeController {
	@RequestMapping("/")
	public String showMainRoot() {
		return "redirect:/mpaUsr/home/main";
	}
	
	@RequestMapping("/mpaUsr/home/main")
	public String showMain() {
		return "mpaUsr/home/main";
	}
}