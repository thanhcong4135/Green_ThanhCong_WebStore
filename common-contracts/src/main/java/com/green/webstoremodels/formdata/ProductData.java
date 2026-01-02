package com.green.webstoremodels.formdata;

public class ProductData {
	private Integer id;
	private String name;
	private String code;
	private String description;
	private String photo;
	private Float price;
	private Float sale_price;
	private Integer categoryId;

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
	public Float getPrice() { return price; }
	public void setPrice(Float price) { this.price = price; }
	public Float getSale_price() { return sale_price; }
	public void setSale_price(Float sale_price) { this.sale_price = sale_price; }
	public Integer getCategoryId() { return categoryId; }
	public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
}
