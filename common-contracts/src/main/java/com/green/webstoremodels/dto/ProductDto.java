package com.green.webstoremodels.dto;

public class ProductDto {
    private Integer id;
    private String name;
    private String code;
    private String description;
    private String photo;
    private Float price;
    private Float salePrice;
    private Integer categoryId;
    private String brandName;
    private Integer brandId;

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
    public Float getSalePrice() { return salePrice; }
    public void setSalePrice(Float salePrice) { this.salePrice = salePrice; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public Integer getBrandId() { return brandId; }
    public void setBrandId(Integer brandId) { this.brandId = brandId; }
}
