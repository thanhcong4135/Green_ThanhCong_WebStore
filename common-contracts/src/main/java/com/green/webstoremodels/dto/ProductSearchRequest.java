package com.green.webstoremodels.dto;

public class ProductSearchRequest {
    private Integer categoryId;
    private Integer brandId;
    private Double priceFrom;
    private Double priceTo;
    private String keyword;
    private String sort; // price_asc, price_desc, newest, popular
    private Integer page = 0;
    private Integer size = 20;

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public Integer getBrandId() { return brandId; }
    public void setBrandId(Integer brandId) { this.brandId = brandId; }
    public Double getPriceFrom() { return priceFrom; }
    public void setPriceFrom(Double priceFrom) { this.priceFrom = priceFrom; }
    public Double getPriceTo() { return priceTo; }
    public void setPriceTo(Double priceTo) { this.priceTo = priceTo; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
}
