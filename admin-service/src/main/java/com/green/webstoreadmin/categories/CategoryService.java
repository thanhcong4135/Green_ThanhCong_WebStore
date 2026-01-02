package com.green.webstoreadmin.categories;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.CategoryDto;

@Service
public class CategoryService {
	
	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${product.service.url:http://localhost:8091/api/categories}")
	private String productServiceUrl;
	
	public List<CategoryDto> getAllCategories(){
		CategoryDto[] categories = restTemplate.getForObject(productServiceUrl, CategoryDto[].class);
		return categories != null ? Arrays.asList(categories) : List.of();
	}
	
	public CategoryDto getCategoryById(int id) {
		String url = UriComponentsBuilder.fromHttpUrl(productServiceUrl).pathSegment(String.valueOf(id)).toUriString();
		return restTemplate.getForObject(url, CategoryDto.class);
	}
	
	public CategoryDto saveCategory(CategoryDto category) {
		if (category.getId() == null) {
			return restTemplate.postForObject(productServiceUrl, category, CategoryDto.class);
		} else {
			String url = UriComponentsBuilder.fromHttpUrl(productServiceUrl).pathSegment(String.valueOf(category.getId())).toUriString();
			restTemplate.put(url, category);
		}
		return category;
	}
	
	public void deleteCategoryById(Integer id) {
		String url = UriComponentsBuilder.fromHttpUrl(productServiceUrl).pathSegment(String.valueOf(id)).toUriString();
		restTemplate.delete(url);
	}

}
