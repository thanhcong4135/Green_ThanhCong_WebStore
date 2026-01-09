package com.green.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.product.domain.Brand;
import com.green.product.repo.BrandRepository;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> findAll() { return brandRepository.findAll(); }

    public Brand findById(Integer id) { return brandRepository.findById(id).orElse(null); }

    public Brand save(Brand brand) { return brandRepository.save(brand); }

    public void delete(Integer id) { brandRepository.deleteById(id); }
}
