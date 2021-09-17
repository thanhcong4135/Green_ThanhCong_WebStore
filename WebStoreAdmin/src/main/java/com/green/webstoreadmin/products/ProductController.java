package com.green.webstoreadmin.products;



import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.green.webstoreadmin.categories.CategoryService;
import com.green.webstoremodels.entities.Category;
import com.green.webstoremodels.entities.Product;
import com.green.webstoremodels.formdata.ProductData;

import com.green.webstoreadmin.file.FileUploadUtil;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	ServletContext context;
	
	@GetMapping("/products")
	public String showProducts(Model model) {
	
		List<Product> listProducts = productService.getAllProducts();
		
		model.addAttribute("listProducts", listProducts);
		
		return "product_list";
	}
	
	@GetMapping("/new-product")
	public String showNewProduct(Model model) {
		List<Category> listCategories = categoryService.getAllCategories();
		Product product = new Product();
		
		model.addAttribute("product", product);
		
		model.addAttribute("listCategories", listCategories);
		return "new_product";
	}
	
	@RequestMapping(value = "/save-product", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product,
			@RequestParam("fileImage") MultipartFile fileImage) throws IOException{
		 	String fileName = StringUtils.cleanPath(fileImage.getOriginalFilename());

	        product.setPhoto(fileName);
	        
	        Product saveProduct = productService.save(product);
	 
	        String uploadDir = "./product-photos/" + saveProduct.getCode();
	        System.out.println("file name: " + fileName);
	        FileUploadUtil.saveFile(uploadDir, fileName, fileImage);
		return "redirect:/products";
	}
	
	@RequestMapping(value = "/update-product", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product){

		productService.save(product);
	 
		return "redirect:/products";
	}
	
	@RequestMapping(value = "/delete-product/{id}", method = RequestMethod.GET)
	public String deleteProduct(@PathVariable(name = "id") Integer id) {
		
		System.out.println("delete product id: " + id);
		
		productService.deleteProdcutById(id);
		
		return "redirect:/products";
	}
	
	@RequestMapping("/edit-product/code={code}")
	public String updateProduct(@PathVariable(name = "code") String code, Model model) {
		
		List<Category> listCategories = categoryService.getAllCategories();
		
		Product entity = productService.getProductByCode(code);
		
		ProductData product = ProductData.copyValueFormEntity(entity);
		
		model.addAttribute("product", product);
		model.addAttribute("listCategories", listCategories);
		
		return "edit_product";
		
	}
	
	@RequestMapping("/detail-product/code={code}")
	public String detailProduct(@PathVariable(name = "code") String code, Model model) {
		
		List<Category> listCategories = categoryService.getAllCategories();
		
		Product entity = productService.getProductByCode(code);
		
		ProductData product = ProductData.copyValueFormEntity(entity);
		
		model.addAttribute("product", product);
		model.addAttribute("listCategories", listCategories);
		
		return "detail_product";
		
	}

}
