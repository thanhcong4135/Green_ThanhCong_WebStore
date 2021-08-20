package com.green.webstoreadmin.categories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.webstoremodels.entities.Category;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories-list")
	public String showCategories(Model model) {
		List<Category> listCategories = categoryService.getAllCategories();
		model.addAttribute("listCategories", listCategories);
		return "category_list";
	}

}
