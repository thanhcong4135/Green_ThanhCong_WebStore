package com.green.product.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.product.domain.Category;
import com.green.product.domain.Product;
import com.green.product.service.ProductService;
import com.green.product.service.CategoryService;
import com.green.webstoremodels.dto.ProductDto;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> list() {
        return productService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable Integer id) {
        Product p = productService.findById(id);
        return p != null ? ResponseEntity.ok(toDto(p)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        Product product = toEntity(dto);
        Product saved = productService.save(product);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Integer id, @RequestBody ProductDto dto) {
        Product existing = productService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        Product entity = toEntity(dto);
        entity.setId(id);
        Product saved = productService.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private CategoryService categoryService;

    private ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setCode(p.getCode());
        dto.setDescription(p.getDescription());
        dto.setPhoto(p.getPhoto());
        dto.setPrice(p.getPrice());
        dto.setSalePrice(p.getSale_price());
        dto.setCategoryId(p.getCategory() != null ? p.getCategory().getId() : null);
        return dto;
    }

    private Product toEntity(ProductDto dto) {
        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setCode(dto.getCode());
        p.setDescription(dto.getDescription());
        p.setPhoto(dto.getPhoto());
        p.setPrice(dto.getPrice());
        p.setSale_price(dto.getSalePrice());
        if (dto.getCategoryId() != null) {
            Category c = categoryService.findById(dto.getCategoryId());
            p.setCategory(c);
        }
        return p;
    }
}
