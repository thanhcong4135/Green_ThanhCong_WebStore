package com.green.webstoremodels.dto;

public class AddressDto {
    private Integer id;
    private String street;
    private String city;
    private Integer customerId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
}
