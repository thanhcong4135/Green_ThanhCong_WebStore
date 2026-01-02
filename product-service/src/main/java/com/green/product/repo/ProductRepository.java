package com.green.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM products WHERE name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<Product> findByNameLike(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.code = :code")
    Product findByCode(@Param("code") String code);

    List<Product> findByCategory_Id(Integer categoryId);
}
