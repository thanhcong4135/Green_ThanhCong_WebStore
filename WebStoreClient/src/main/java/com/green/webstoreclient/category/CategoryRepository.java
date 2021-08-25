package com.green.webstoreclient.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.green.webstoremodels.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>  {
	@Query("SELECT c FROM Category c WHERE c.parent = null")
	public List<Category> getRootCategory();
	
}
