package com.green.webstoreclient.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.webstoremodels.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query(value = "SELECT * FROM products  WHERE name like %?1%", nativeQuery = true)
	public List<Product> getProductByName(@Param ("name") String name);
	
	@Query(value = "SELECT * FROM products WHERE MATCH(name) AGAINST (?1)", nativeQuery = true)
	public List<Product> fullTextSearchProductByName(String name);
	
	@Query(value = "SELECT * FROM products WHERE category_id  = ?1 ", nativeQuery =  true)
	public List<Product> getProductByCategoryId(@Param("long") Integer categoryid );
	
	@Query(value = "SELECT * FROM products WHERE MATCH(name) AGAINST (?1) LIMIT 5", nativeQuery = true)
	public List<Product> autoCompleteProductByName(String name);
	
	@Query("SELECT p FROM Product p WHERE p.code = :code")
	public Product getProductByCode(@Param("code") String code);
}
