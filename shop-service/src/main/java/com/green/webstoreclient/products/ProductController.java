package com.green.webstoreclient.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.green.webstoreclient.category.CategoryService;
import com.green.webstoremodels.dto.CategoryDto;
import com.green.webstoremodels.dto.ProductDto;

@RestController
@RequestMapping("/api/shop/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<Page<ProductDto>> searchProducts(@RequestParam(required = false) Integer categoryId,
														   @RequestParam(required = false) Integer brandId,
														   @RequestParam(required = false) Double priceFrom,
														   @RequestParam(required = false) Double priceTo,
														   @RequestParam(required = false) String keyword,
														   @RequestParam(required = false) String sort,
														   @RequestParam(required = false, defaultValue = "0") Integer page,
														   @RequestParam(required = false, defaultValue = "9") Integer size) {
		return ResponseEntity.ok(productService.search(categoryId, brandId, priceFrom, priceTo, keyword, sort, page, size));
	}
	
	@GetMapping("/category/{id}")
	public ResponseEntity<List<ProductDto>> byCategory(@PathVariable(name = "id") int id) {
		List<ProductDto> products = productService.getProductCategory(id);
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> categories() {
		return ResponseEntity.ok(categoryService.getAllCategory());
	}

}
