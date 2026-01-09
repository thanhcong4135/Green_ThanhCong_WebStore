package com.green.product.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.product.domain.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findByCode(String code);
}
