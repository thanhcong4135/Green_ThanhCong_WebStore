package com.green.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.product.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
