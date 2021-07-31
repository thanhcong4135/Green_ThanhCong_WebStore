package com.green.webstoreadmin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
//	@RequestMapping("/login")
//	public String showLoginView(Model model) {
//		System.out.println("show Login View: return login");
//		return "login";
//	}
	
	@GetMapping("/login")
	public String showLoginView() {
		return "login";
	}
	
	
	@RequestMapping("/")
	public String showDashboard(Model model) {
		System.out.println("show Home View: return home");
		return "index";
	}

}
