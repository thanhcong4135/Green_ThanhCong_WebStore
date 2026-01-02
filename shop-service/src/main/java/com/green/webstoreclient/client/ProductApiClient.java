package com.green.webstoreclient.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.ProductDto;

@Component
public class ProductApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${product.service.products-url:http://localhost:8091/api/products}")
    private String productsUrl;

    public List<ProductDto> findAll() {
        ProductDto[] products = restTemplate.getForObject(productsUrl, ProductDto[].class);
        return products != null ? Arrays.asList(products) : List.of();
    }

    public ProductDto findById(Integer id) {
        String url = UriComponentsBuilder.fromHttpUrl(productsUrl)
                .pathSegment(String.valueOf(id))
                .toUriString();
        return restTemplate.getForObject(url, ProductDto.class);
    }

    public List<ProductDto> findByCategory(Integer categoryId) {
        // product-service chưa có filter theo category, tạm filter client-side
        return findAll().stream()
                .filter(p -> p.getCategoryId() != null && p.getCategoryId().equals(categoryId))
                .toList();
    }
}
