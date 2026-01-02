package com.green.webstoremodels.formdata;

public class CategoryData {
	private Integer id;
	private String name;
	private String code;
	private String description;
	private String photo;
	private Integer parentId;

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getCode() { return code; }
	public void setCode(String code) { this.code = code; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getPhoto() { return photo; }
	public void setPhoto(String photo) { this.photo = photo; }
	public Integer getParentId() { return parentId; }
	public void setParentId(Integer parentId) { this.parentId = parentId; }
}
