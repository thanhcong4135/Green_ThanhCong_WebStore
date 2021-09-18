package com.green.webstoreclient.products;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.green.webstoreclient.products.ProductService;
import com.green.webstoremodels.entities.Category;
import com.green.webstoremodels.entities.Product;
import com.green.webstoremodels.formdata.ProductData;
import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.carts.CartSessionUtil;
import com.green.webstoreclient.category.CategoryService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = {"/allproducts"})
	public String showProductView(HttpServletRequest request,Model model) {		
		
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());
		model.addAttribute("cartSize", cartInfo.getCartLines().size());
		return showProductPageView(request, model, 1);
	}
	
	@RequestMapping(value = {"/allproducts/{pageNum}"})
	public String showProductPageView(HttpServletRequest request, Model model, @PathVariable(name = "pageNum") int pageNum) {
		
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		Page<Product> pageProduct = productService.getProductsWithPage(pageNum);
		List<Product> products = pageProduct.getContent();
		List<Category> categories = categoryService.getAllCategory();
		model.addAttribute("products", products);
		
		long startCount = (pageNum - 1) * ProductService.PAGE_SIZE + 1;
		long endCount = startCount + ProductService.PAGE_SIZE - 1;
		
		if( endCount > pageProduct.getTotalElements()) {
			endCount = pageProduct.getTotalElements();
		}
		model.addAttribute("cartInfo", cartInfo);
		
		model.addAttribute("totalPages", pageProduct.getTotalPages());
		model.addAttribute("totalProducts", pageProduct.getTotalElements());
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("categories", categories);
		
		return "all_product";
	}
	
	@GetMapping("/loadproduct-bycategoryid/cateid={id}")
	public String updateProduct(HttpServletRequest request,@PathVariable(name = "id") int id, Model model) {
		
		
		Category category = categoryService.getById(id);
		List<Product> products = productService.getProductCategory(category);
		List<Category> listCategories = categoryService.getAllCategory();
//		ProductData product = ProductData.copyValueFormEntity(entity);
		
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("products", products);
		model.addAttribute("listCategories", listCategories);
		
		return "loadproduct_bycategory";
		
	}

}
