package com.green.webstoreadmin.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	public List<Product> getAllProducts() {
		return repository.findAll();
	}
	
	public Product getProductByCode(String code) {
		
		return repository.getByCode(code);
	}
	
	public void saveProduct(Product product) {
		repository.save(product);
	}
	
	public void deleteProdcutByCode(String code) {
		//Viet query///
	}
	
	public void deleteProdcutById(Integer id) {
		repository.deleteById(id);
	}
}
