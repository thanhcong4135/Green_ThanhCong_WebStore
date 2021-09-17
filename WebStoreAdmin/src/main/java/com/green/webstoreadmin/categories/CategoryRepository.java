package com.green.webstoreadmin.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webstoremodels.entities.Category;
import com.green.webstoremodels.entities.Product;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query("SELECT c FROM Category c WHERE c.id = :id")
	public Category getById(@Param("id") int id);

}
