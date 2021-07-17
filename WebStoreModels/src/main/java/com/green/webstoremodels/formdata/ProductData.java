package com.green.webstoremodels.formdata;

import com.green.webstoremodels.entities.Product;

public class ProductData {

	private Integer id;

	private String name;
	
	private String code;
	
	private String description;
	
	private String photo;
	
	private Float price;
	
	private Float sale_price;

	public static ProductData copyValueFormEntity(Product enetity) {
		ProductData data = new ProductData();
		
		data.id = enetity.getId();
		data.name = enetity.getName();
		data.code = enetity.getCode();
		data.description = enetity.getDescription();
		data.price = enetity.getPrice();
		data.sale_price = enetity.getSale_price();
		
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getSale_price() {
		return sale_price;
	}

	public void setSale_price(Float sale_price) {
		this.sale_price = sale_price;
	}
}
