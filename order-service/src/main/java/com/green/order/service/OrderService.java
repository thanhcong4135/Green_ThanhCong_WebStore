package com.green.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.order.domain.Order;
import com.green.order.domain.OrderDetail;
import com.green.order.domain.OrderStatus;
import com.green.order.domain.Customer;
import com.green.order.domain.OrderTrackingEvent;
import com.green.order.repo.OrderRepository;
import com.green.webstoremodels.dto.CheckoutItemDto;
import com.green.webstoremodels.dto.CheckoutRequest;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderTrackingService orderTrackingService;

    public List<Order> findByOrderCodeAndPhone(String orderCode, String phone) {
        return orderRepository.findByOrderCodeAndPhone(orderCode, phone);
    }

    public List<Order> findByCustomer(Integer customerId) {
        return orderRepository.findByCustomer_CustomerId(customerId);
    }

    public List<Order> findAll() { return orderRepository.findAll(); }

    public Order findById(Integer id) { return orderRepository.findById(id).orElse(null); }

    public Order save(Order order) { return orderRepository.save(order); }

    public void delete(Integer id) { orderRepository.deleteById(id); }

    public Order updateStatus(Order order, OrderStatus newStatus, String description, String location) {
        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);
        orderTrackingService.addEvent(saved, newStatus.name(), description != null ? description : "Status updated", location);
        return saved;
    }

    public Order checkout(CheckoutRequest request) {
        Order order = new Order();
        order.setAddress(request.getAddress());
        order.setReceiverName(request.getReceiverName());
        order.setReceiverPhone(request.getReceiverPhone());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setOrderCode("ORD-" + System.currentTimeMillis());
        order.setStatus(OrderStatus.NEW);
        order.setVoucherCode(request.getVoucherCode());
        order.setPaymentProvider(request.getPaymentProvider());
        order.setPaymentIntentId(request.getPaymentIntentId());
        order.setShippingFee(request.getShippingFee() != null ? request.getShippingFee() : 0d);
        order.setDiscountAmount(request.getDiscountAmount() != null ? request.getDiscountAmount() : 0d);

        if (request.getCustomerId() != null) {
            Customer customer = customerService.findById(request.getCustomerId());
            order.setCustomer(customer);
        }

        double total = 0d;
        if (request.getItems() != null) {
            for (CheckoutItemDto item : request.getItems()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(order);
                detail.setProductName(item.getProductName());
                detail.setPrice(item.getPrice());
                detail.setQuantity(item.getQuantity());
                total += (item.getPrice() != null ? item.getPrice() : 0) * (item.getQuantity() != null ? item.getQuantity() : 1);
                order.getOrderDetails().add(detail);
            }
        }
        double shipping = order.getShippingFee() != null ? order.getShippingFee() : 0d;
        double discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : 0d;
        double finalTotal = Math.max(0d, total + shipping - discount);
        order.setTotalPrice(finalTotal);
        if ("ONLINE".equalsIgnoreCase(order.getPaymentMethod())) {
            order.setPaymentStatus("PENDING_PAYMENT");
        } else {
            order.setPaymentStatus("PAID");
        }

        Order saved = orderRepository.save(order);
        orderTrackingService.addEvent(saved, OrderStatus.NEW.name(), "Order created", null);
        return saved;
    }

    public Order attachPaymentIntent(Integer orderId, String provider, String intentId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setPaymentProvider(provider);
        order.setPaymentIntentId(intentId);
        order.setPaymentStatus("PENDING_PAYMENT");
        return orderRepository.save(order);
    }

    public void handlePaymentWebhook(String paymentIntentId, String status) {
        orderRepository.findByPaymentIntentId(paymentIntentId).ifPresent(order -> {
            order.setPaymentStatus(status);
            if ("SUCCEEDED".equalsIgnoreCase(status)) {
                updateStatus(order, OrderStatus.PROCESSING, "Payment succeeded", null);
            } else if ("FAILED".equalsIgnoreCase(status)) {
                orderRepository.save(order);
            }
        });
    }
}
