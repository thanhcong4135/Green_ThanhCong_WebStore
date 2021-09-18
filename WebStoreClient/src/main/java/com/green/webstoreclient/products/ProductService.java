package com.green.webstoreclient.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.Category;
import com.green.webstoremodels.entities.Product;

@Service
public class ProductService {
	public static int PAGE_SIZE = 9;
	
	@Autowired
	private ProductRepository repository;
	
	public List<Product> getAllProduct() {
		return repository.findAll();
	}
	
	public Page<Product> getProductsWithPage(int pageNum) {
		Pageable pageable;
		
		if (pageNum >= 1) {
			pageable = PageRequest.of(pageNum - 1, ProductService.PAGE_SIZE);
		} else {
			pageable = PageRequest.of(0, ProductService.PAGE_SIZE);
		}
		
		return repository.findAll(pageable);
	}
	
	public List<Product> getProductByName(String name){
		return repository.getProductByName(name);
	}
	
	public List<Product> fullTextSearchProductByName(String name){
		return repository.fullTextSearchProductByName(name);
	}
	
	public List<Product> getProductCategory(Category category) {
		List<Product> listProducts = repository.getProductByCategoryId(category.getId());
		return listProducts;
	}
	
	public Product getByCode(String code) {
		return repository.getProductByCode(code);
	}
}
