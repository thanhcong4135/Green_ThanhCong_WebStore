package com.green.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.green.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM products WHERE name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<Product> findByNameLike(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.code = :code")
    Product findByCode(@Param("code") String code);

    List<Product> findByCategory_Id(Integer categoryId);

    @Query("SELECT p FROM Product p WHERE "
        + "(:categoryId IS NULL OR p.category.id = :categoryId) AND "
        + "(:brandName IS NULL OR LOWER(p.brandName) = LOWER(:brandName)) AND "
        + "(:priceFrom IS NULL OR p.price >= :priceFrom) AND "
        + "(:priceTo IS NULL OR p.price <= :priceTo) AND "
        + "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Product> search(@Param("categoryId") Integer categoryId,
                         @Param("brandName") String brandName,
                         @Param("priceFrom") Double priceFrom,
                         @Param("priceTo") Double priceTo,
                         @Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE "
        + "(:categoryId IS NULL OR p.category.id = :categoryId) AND "
        + "(:brandId IS NULL OR p.brand.id = :brandId) AND "
        + "(:priceFrom IS NULL OR p.price >= :priceFrom) AND "
        + "(:priceTo IS NULL OR p.price <= :priceTo) AND "
        + "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> searchPage(@Param("categoryId") Integer categoryId,
                             @Param("brandId") Integer brandId,
                             @Param("priceFrom") Double priceFrom,
                             @Param("priceTo") Double priceTo,
                             @Param("keyword") String keyword,
                             Pageable pageable);
}
