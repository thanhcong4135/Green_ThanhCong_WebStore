package com.green.webstoreclient.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.CategoryDto;

@Component
public class CategoryApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${product.service.categories-url:http://localhost:8091/api/categories}")
    private String categoriesUrl;

    public List<CategoryDto> findAll() {
        CategoryDto[] categories = restTemplate.getForObject(categoriesUrl, CategoryDto[].class);
        return categories != null ? Arrays.asList(categories) : List.of();
    }

    public CategoryDto findById(Integer id) {
        String url = UriComponentsBuilder.fromHttpUrl(categoriesUrl)
                .pathSegment(String.valueOf(id))
                .toUriString();
        return restTemplate.getForObject(url, CategoryDto.class);
    }
}
