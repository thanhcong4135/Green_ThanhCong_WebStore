package com.green.webstoreclient.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.client.ProductApiClient;
import com.green.webstoreclient.client.ProductApiClient.PageResult;
import com.green.webstoremodels.dto.ProductDto;

@Service
public class ProductService {
	public static int PAGE_SIZE = 9;
	
	@Autowired
	private ProductApiClient productApiClient;
	
	public Page<ProductDto> search(Integer categoryId, Integer brandId, Double priceFrom, Double priceTo, String keyword, String sort, Integer pageNum, Integer size) {
		PageResult<ProductDto> result = productApiClient.search(categoryId, brandId, priceFrom, priceTo, keyword, sort, pageNum, size);
		if (result == null) return Page.empty();
		return new PageImpl<>(result.content != null ? result.content : List.of(),
				org.springframework.data.domain.PageRequest.of(result.number, result.size),
				result.totalElements);
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
