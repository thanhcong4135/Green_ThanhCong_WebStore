package com.green.webstoreclient.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.client.ProductApiClient;
import com.green.webstoremodels.dto.ProductDto;

@Service
public class ProductService {
	public static int PAGE_SIZE = 9;
	
	@Autowired
	private ProductApiClient productApiClient;
	
	public List<ProductDto> getAllProduct() {
		return productApiClient.findAll();
	}
	
	public Page<ProductDto> getProductsWithPage(int pageNum) {
		List<ProductDto> all = productApiClient.findAll();
		if (pageNum < 1) pageNum = 1;
		int pageIndex = pageNum - 1;
		int start = pageIndex * PAGE_SIZE;
		int end = Math.min(start + PAGE_SIZE, all.size());
		List<ProductDto> content = start >= all.size() ? List.of() : all.subList(start, end);
		Pageable pageable = PageRequest.of(pageIndex, PAGE_SIZE);
		return new PageImpl<>(content, pageable, all.size());
	}
	
	public List<ProductDto> getProductByName(String name){
		return productApiClient.findAll().stream()
				.filter(p -> p.getName() != null && p.getName().toLowerCase().contains(name.toLowerCase()))
				.toList();
	}
	
	public List<ProductDto> fullTextSearchProductByName(String name){
		return getProductByName(name);
	}
	
	public List<ProductDto> getProductCategory(Integer categoryId) {
		return productApiClient.findByCategory(categoryId);
	}
	
	public ProductDto getById(Integer id) {
		return productApiClient.findById(id);
	}

	public ProductDto getByCode(String code) {
		return productApiClient.findAll().stream()
				.filter(p -> p.getCode() != null && p.getCode().equalsIgnoreCase(code))
				.findFirst()
				.orElse(null);
	}
}
