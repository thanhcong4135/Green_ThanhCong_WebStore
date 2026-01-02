package com.green.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.product.domain.Category;
import com.green.product.repo.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() { return categoryRepository.findAll(); }

    public Category findById(Integer id) { return categoryRepository.findById(id).orElse(null); }

    public Category save(Category category) { return categoryRepository.save(category); }

    public void delete(Integer id) { categoryRepository.deleteById(id); }
}
