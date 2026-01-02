package com.green.webstoreclient.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.carts.CartSessionUtil;
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
	public ResponseEntity<Page<ProductDto>> listProducts() {		
		return ResponseEntity.ok(productService.getProductsWithPage(1));
	}
	
	@GetMapping("/page/{pageNum}")
	public ResponseEntity<Page<ProductDto>> showProductPage(@PathVariable(name = "pageNum") int pageNum) {
		Page<ProductDto> pageProduct = productService.getProductsWithPage(pageNum);
		return ResponseEntity.ok(pageProduct);
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
