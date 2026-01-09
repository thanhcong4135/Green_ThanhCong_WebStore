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

import com.green.product.domain.Brand;
import com.green.product.service.BrandService;
import com.green.webstoremodels.dto.BrandDto;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public List<BrandDto> list() {
        return brandService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> get(@PathVariable Integer id) {
        Brand b = brandService.findById(id);
        return b != null ? ResponseEntity.ok(toDto(b)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BrandDto> create(@RequestBody BrandDto dto) {
        Brand saved = brandService.save(toEntity(dto));
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDto> update(@PathVariable Integer id, @RequestBody BrandDto dto) {
        Brand existing = brandService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        Brand entity = toEntity(dto);
        entity.setId(id);
        Brand saved = brandService.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private BrandDto toDto(Brand b) {
        BrandDto dto = new BrandDto();
        dto.setId(b.getId());
        dto.setName(b.getName());
        dto.setCode(b.getCode());
        return dto;
    }

    private Brand toEntity(BrandDto dto) {
        Brand b = new Brand();
        b.setId(dto.getId());
        b.setName(dto.getName());
        b.setCode(dto.getCode());
        return b;
    }
}
