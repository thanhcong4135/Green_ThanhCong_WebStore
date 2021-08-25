package com.green.webstoreadmin.categories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value = "/delete-category/{id}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable(name = "id") Integer id) {
		
		System.out.println("delete category id: " + id);
		
		categoryService.deleteCategoryById(id);
		
		return "redirect:/category_list";
	}
	
	@GetMapping("/addnew-category")
	public String showFormAddNewCategory(Model model) {
		List<Category> listCategories = categoryService.getAllCategories();
		Category category = new Category();
		
		model.addAttribute("category", category);
		
		model.addAttribute("listCategories", listCategories);
		return "form_addnew_category";
	}
	
	@RequestMapping(value = "/save-category",method = RequestMethod.POST)
	private String saveCategory(@ModelAttribute("category")Category category) {
		category.setEnabled(true);
		
		categoryService.saveCategory(category);
		return "redirect:/category_list";
	}

}
