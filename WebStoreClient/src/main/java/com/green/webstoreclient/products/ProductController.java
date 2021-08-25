package com.green.webstoreclient.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.webstoreclient.products.ProductService;
import com.green.webstoremodels.entities.Product;
import com.green.webstoreclient.category.CategoryService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = {"/allproducts"})
	public String showProductView(Model model) {		
		return showProductPageView(model, 1);
		
//		List<Product> products = productService.getAllProduct();
//		model.addAttribute("products", products);
	}
	
	@RequestMapping(value = {"/allproducts/{pageNum}"})
	public String showProductPageView(Model model, @PathVariable(name = "pageNum") int pageNum) {
		
		Page<Product> pageProduct = productService.getProductsWithPage(pageNum);
		List<Product> products = pageProduct.getContent();
		
		long startCount = (pageNum - 1) * ProductService.PAGE_SIZE + 1;
		long endCount = startCount + ProductService.PAGE_SIZE - 1;
		
		if( endCount > pageProduct.getTotalElements()) {
			endCount = pageProduct.getTotalElements();
		}
		
		model.addAttribute("products", products);
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("totalProducts", pageProduct.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		
		return "all_product";
	}

}
