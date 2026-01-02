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
import com.green.product.service.CategoryService;
import com.green.webstoremodels.dto.CategoryDto;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> list() {
        return categoryService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable Integer id) {
        Category c = categoryService.findById(id);
        return c != null ? ResponseEntity.ok(toDto(c)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto) {
        Category saved = categoryService.save(toEntity(dto));
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Integer id, @RequestBody CategoryDto dto) {
        Category existing = categoryService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        Category entity = toEntity(dto);
        entity.setId(id);
        return ResponseEntity.ok(toDto(categoryService.save(entity)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private CategoryDto toDto(Category c) {
        CategoryDto dto = new CategoryDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setCode(c.getCode());
        dto.setDescription(c.getDescription());
        dto.setPhoto(c.getPhoto());
        dto.setParentId(c.getParent() != null ? c.getParent().getId() : null);
        return dto;
    }

    private Category toEntity(CategoryDto dto) {
        Category c = new Category();
        c.setId(dto.getId());
        c.setName(dto.getName());
        c.setCode(dto.getCode());
        c.setDescription(dto.getDescription());
        c.setPhoto(dto.getPhoto());
        // parent mapping can be added if needed
        return c;
    }
}
