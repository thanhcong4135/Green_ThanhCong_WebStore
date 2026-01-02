package com.green.webstoreadmin.products;



import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.green.webstoreadmin.categories.CategoryService;
import com.green.webstoreadmin.file.FileUploadUtil;
import com.green.webstoremodels.dto.CategoryDto;
import com.green.webstoremodels.dto.ProductDto;

@RestController
@RequestMapping("/api/admin/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping
	public List<ProductDto> listProducts() {
		return productService.getAllProducts();
	}
	
	@PostMapping
	public ResponseEntity<ProductDto> saveProduct(@ModelAttribute("product") ProductDto product,
			@RequestParam(value = "fileImage", required = false) MultipartFile fileImage) throws IOException{
		 	if (fileImage != null && !fileImage.isEmpty()) {
		 		String fileName = StringUtils.cleanPath(fileImage.getOriginalFilename());
		 		product.setPhoto(fileName);
		 		String uploadDir = "./product-photos/" + product.getCode();
		 		FileUploadUtil.saveFile(uploadDir, fileName, fileImage);
		 	}
	        ProductDto saved = productService.save(product);
	        return ResponseEntity.ok(saved);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "id") Integer id, @ModelAttribute("product") ProductDto product){
		product.setId(id);
		ProductDto saved = productService.save(product);
		return ResponseEntity.ok(saved);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Integer id) {
		productService.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping("/{id}")
	public ResponseEntity<ProductDto> detailProduct(@PathVariable(name = "id") Integer id) {
		ProductDto product = productService.getProductById(id);
		return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
	}

}
