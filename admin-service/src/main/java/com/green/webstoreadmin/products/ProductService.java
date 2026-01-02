package com.green.webstoreadmin.products;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.ProductDto;

@Service
public class ProductService {

	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${product.service.products-url:http://localhost:8091/api/products}")
	private String productServiceUrl;
	
	public List<ProductDto> getAllProducts() {
		ProductDto[] products = restTemplate.getForObject(productServiceUrl, ProductDto[].class);
		return products != null ? Arrays.asList(products) : List.of();
	}
	
	public ProductDto getProductById(Integer id) {
		String url = UriComponentsBuilder.fromHttpUrl(productServiceUrl).pathSegment(String.valueOf(id)).toUriString();
		return restTemplate.getForObject(url, ProductDto.class);
	}
	
	public ProductDto save(ProductDto product) {
		if (product.getId() == null) {
			return restTemplate.postForObject(productServiceUrl, product, ProductDto.class);
		}
		String url = UriComponentsBuilder.fromHttpUrl(productServiceUrl).pathSegment(String.valueOf(product.getId())).toUriString();
		restTemplate.put(url, product);
		return product;
	}
	
	public void deleteProductById(Integer id) {
		String url = UriComponentsBuilder.fromHttpUrl(productServiceUrl).pathSegment(String.valueOf(id)).toUriString();
		restTemplate.delete(url);
	}
}
