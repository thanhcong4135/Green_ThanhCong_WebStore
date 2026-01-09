package com.green.product.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.product.domain.Product;
import com.green.product.repo.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() { return productRepository.findAll(); }

    public Product findById(Integer id) { return productRepository.findById(id).orElse(null); }

    public Product save(Product product) { return productRepository.save(product); }

    public void delete(Integer id) { productRepository.deleteById(id); }

    public List<Product> search(Integer categoryId, String brandName, Double priceFrom, Double priceTo, String keyword, String sort) {
        List<Product> products = productRepository.search(categoryId, brandName, priceFrom, priceTo, keyword);
        if (sort == null) return products;
        return switch (sort) {
            case "price_asc" -> products.stream()
                    .sorted(Comparator.comparingDouble(this::effectivePrice))
                    .collect(Collectors.toList());
            case "price_desc" -> products.stream()
                    .sorted(Comparator.comparingDouble(this::effectivePrice).reversed())
                    .collect(Collectors.toList());
            default -> products;
        };
    }

    private double effectivePrice(Product p) {
        return (p.getSale_price() != null && p.getSale_price() > 0) ? p.getSale_price() : p.getPrice();
    }

    public Page<Product> searchPage(Integer categoryId, Integer brandId, Double priceFrom, Double priceTo, String keyword, String sort, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page != null && page >=0 ? page : 0,
                size != null && size > 0 ? size : 20,
                resolveSort(sort));
        return productRepository.searchPage(categoryId, brandId, priceFrom, priceTo, keyword, pageable);
    }

    private Sort resolveSort(String sort) {
        if (sort == null) return Sort.unsorted();
        return switch (sort) {
            case "price_asc" -> Sort.by(Sort.Direction.ASC, "sale_price", "price");
            case "price_desc" -> Sort.by(Sort.Direction.DESC, "sale_price", "price");
            case "name_asc" -> Sort.by(Sort.Direction.ASC, "name");
            default -> Sort.unsorted();
        };
    }
}
