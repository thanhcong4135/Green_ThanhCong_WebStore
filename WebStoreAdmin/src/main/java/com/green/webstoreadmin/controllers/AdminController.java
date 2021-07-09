package com.green.webstoreadmin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AdminController {
	
	@RequestMapping("/")
	public String showDashboard() {
		return "NewFile";
	}
}
