package com.green.webstoreadmin.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.webstoreadmin.products.ProductService;
import com.green.webstoremodels.entities.Category;
import com.green.webstoremodels.entities.Product;
import com.green.webstoremodels.formdata.ProductData;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public String showProducts(Model model) {
	
		List<Product> listProducts = productService.getAllProducts();
		
		model.addAttribute("listProducts", listProducts);
		
		return "product_list";
	}

	@GetMapping("/new-product")
	public String showNewProduct(Model model) {
	
		Product product = new Product();

		model.addAttribute("product", product);
		
		return "new_product";
	}
	
	@RequestMapping(value = "/save-product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {
		
		Category category = new Category();
		
		category.setId(1);
		product.setCategory(category);
		product.setEnabled(true);
		
		productService.saveProduct(product);
		
		return "redirect:/products";
	}
	
	@RequestMapping(value = "/update-product", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") ProductData product) {
		
		System.out.println("updateProduct: " + product.getCode());
		
		Product entity = productService.getProductByCode(product.getCode());
		
		if (entity != null) {
			entity.updateFormData(product);
			productService.saveProduct(entity);
		}
		
		return "redirect:/products";
	}
	
	@RequestMapping(value = "/delete-product/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable(name = "id") Integer id) {
		
		System.out.println("delete product id: " + id);
		
		productService.deleteProdcutById(id);
		
		return "redirect:/products";
		//return "product_list";
	}
	
	@RequestMapping("/edit-product/{code}")
	public String updateProduct(@PathVariable(name = "code") String code, Model model) {
		//ModelAndView modelAndView = new ModelAndView("update_product");
		
		Product entity = productService.getProductByCode(code);
		
		ProductData product = ProductData.copyValueFormEntity(entity);
		
		model.addAttribute("product", product);
		
		return "update_product";
		
	}

}
