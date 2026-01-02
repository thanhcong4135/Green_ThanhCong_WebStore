package com.green.webstoreclient.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.client.CategoryApiClient;
import com.green.webstoremodels.dto.CategoryDto;

@Service
public class CategoryService {
	@Autowired
	private CategoryApiClient categoryApiClient;
	
	public List<CategoryDto> getAllCategory(){
		return categoryApiClient.findAll();
	}
	
	public List<CategoryDto> getRootCategory() {
		return categoryApiClient.findAll().stream()
				.filter(c -> c.getParentId() == null)
				.toList();
	}
	
	public CategoryDto getById(int id) {
		return categoryApiClient.findById(id);
	}
	
	public List<CategoryDto> getListParent(CategoryDto category){
		List<CategoryDto> listParent = new ArrayList<CategoryDto>();
		if(category != null) {
			CategoryDto current = category;
			while(current != null && current.getParentId() != null) {
				CategoryDto parent = categoryApiClient.findById(current.getParentId());
				if (parent == null) break;
				listParent.add(0, parent);
				current = parent;
			}
			listParent.add(category);
		}
		return listParent;
	}
}
