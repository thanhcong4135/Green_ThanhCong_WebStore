package com.green.webstoremodels.dto;

import java.util.List;

public class CheckoutRequest {
    private Integer customerId;
    private String receiverName;
    private String receiverPhone;
    private String address;
    private String paymentMethod;
    private String voucherCode;
    private Double shippingFee;
    private Double discountAmount;
    private String paymentProvider;
    private String paymentIntentId;
    private List<CheckoutItemDto> items;

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getVoucherCode() { return voucherCode; }
    public void setVoucherCode(String voucherCode) { this.voucherCode = voucherCode; }
    public Double getShippingFee() { return shippingFee; }
    public void setShippingFee(Double shippingFee) { this.shippingFee = shippingFee; }
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    public String getPaymentProvider() { return paymentProvider; }
    public void setPaymentProvider(String paymentProvider) { this.paymentProvider = paymentProvider; }
    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }
    public List<CheckoutItemDto> getItems() { return items; }
    public void setItems(List<CheckoutItemDto> items) { this.items = items; }
}
