package com.green.webstoremodels.formdata;

import com.green.webstoremodels.entities.Category;

public class CategoryData {
	private Integer id;
	
	private String name;
	
	private String code;
	
	private String description;
	
	private String photo;
	
	private Category parent;
	
	public static CategoryData copyValueFormEntity(Category entity) {
		CategoryData data = new CategoryData();
		
		data.id = entity.getId();
		data.name = entity.getName();
		data.code = entity.getCode();
		data.description = entity.getDescription();
		data.photo = entity.getPhoto();
		data.parent = entity.getParent();
		
		return data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	
}
