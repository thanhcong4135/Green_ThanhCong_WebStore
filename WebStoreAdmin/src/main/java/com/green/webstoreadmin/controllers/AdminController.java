package com.green.webstoreadmin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.webstoreadmin.storage.StorageFileNotFoundException;
import com.green.webstoreadmin.storage.StorageService;

@Controller
public class AdminController {
	
	private final StorageService storageService;
	
	@Autowired
	public AdminController(StorageService storageService) {
		this.storageService = storageService;
	}
	
//	@RequestMapping("/login")
//	public String showLoginView(Model model) {
//		System.out.println("show Login View: return login");
//		return "login";
//	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename){
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc){
		return ResponseEntity.notFound().build();
	}
	
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
