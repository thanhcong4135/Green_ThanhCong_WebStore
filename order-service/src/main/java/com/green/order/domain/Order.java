package com.green.order.domain;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_provider")
    private String paymentProvider;

    @Column(name = "payment_intent_id")
    private String paymentIntentId;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "orderCode")
    private String orderCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @Transient
    public String getCustomerName() {
        return customer != null ? customer.getFirstName() + " " + customer.getLastName() : null;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getPaymentProvider() { return paymentProvider; }
    public void setPaymentProvider(String paymentProvider) { this.paymentProvider = paymentProvider; }
    public String getPaymentIntentId() { return paymentIntentId; }
    public void setPaymentIntentId(String paymentIntentId) { this.paymentIntentId = paymentIntentId; }
    public Double getShippingFee() { return shippingFee; }
    public void setShippingFee(Double shippingFee) { this.shippingFee = shippingFee; }
    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }
    public String getVoucherCode() { return voucherCode; }
    public void setVoucherCode(String voucherCode) { this.voucherCode = voucherCode; }
    public Set<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(Set<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public String getOrderCode() { return orderCode; }
    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }
}
