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
        String url = UriComponentsBuilder.fromHttpUrl(productsUrl)
                .path("/search")
                .queryParam("categoryId", categoryId)
                .toUriString();
        ProductDto[] products = restTemplate.getForObject(url, ProductDto[].class);
        return products != null ? Arrays.asList(products) : List.of();
    }

    public PageResult<ProductDto> search(Integer categoryId, Integer brandId, Double priceFrom, Double priceTo, String keyword, String sort, Integer page, Integer size) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(productsUrl)
                .path("/search");
        if (categoryId != null) builder.queryParam("categoryId", categoryId);
        if (brandId != null) builder.queryParam("brandId", brandId);
        if (priceFrom != null) builder.queryParam("priceFrom", priceFrom);
        if (priceTo != null) builder.queryParam("priceTo", priceTo);
        if (keyword != null && !keyword.isBlank()) builder.queryParam("keyword", keyword);
        if (sort != null && !sort.isBlank()) builder.queryParam("sort", sort);
        if (page != null) builder.queryParam("page", page);
        if (size != null) builder.queryParam("size", size);

        return restTemplate.getForObject(builder.toUriString(), PageResult.class);
    }

    public static class PageResult<T> {
        public List<T> content;
        public int number;
        public int size;
        public long totalElements;
        public int totalPages;
        public boolean last;
    }
}
