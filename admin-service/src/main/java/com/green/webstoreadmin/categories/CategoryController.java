package com.green.webstoreadmin.categories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.webstoremodels.dto.CategoryDto;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public List<CategoryDto> listCategories() {
		return categoryService.getAllCategories();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Integer id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@ModelAttribute("category")CategoryDto category) {
		CategoryDto saved = categoryService.saveCategory(category);
		return ResponseEntity.ok(saved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable(name = "id") int id, @ModelAttribute("category")CategoryDto category) {
		category.setId(id);
		CategoryDto saved = categoryService.saveCategory(category);
		return ResponseEntity.ok(saved);
	}

}
