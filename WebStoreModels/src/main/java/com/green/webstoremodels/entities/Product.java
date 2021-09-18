package com.green.webstoremodels.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.green.webstoremodels.formdata.ProductData;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "photo")
	private String photo;
	
	@Column(name = "price")
	private Float price;
	
	@Column(name = "sale_price")
	private Float sale_price;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	

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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
	public Product(String name, String code, String description, String photo, Float price, Float sale_price,
			Boolean enabled) {
		super();
		this.name = name;
		this.code = code;
		this.description = description;
		this.photo = photo;
		this.price = price;
		this.sale_price = sale_price;
		this.enabled = enabled;
	}
	
	public Product() {
		super();
	}

	@Transient
	public void updateFormData(ProductData data) {
		this.name = data.getName();
		this.code = data.getCode();
		this.description = data.getDescription();
		this.price = data.getPrice();
		this.sale_price = data.getSale_price();
	}
	
	@Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;
         
        return "/product-photos/" + code + "/" + photo;
    }
	
	@Transient
    public String getPhotoToClient() {
        if (photo == null) return null;
         
        return "/assets/img/product-img/" + photo;
    }

}
