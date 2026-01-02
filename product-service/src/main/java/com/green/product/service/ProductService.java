package com.green.product.service;

import java.util.List;

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
}
