package com.green.order.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import com.green.order.domain.Order;
import com.green.order.domain.OrderStatus;
import com.green.order.service.OrderService;
import com.green.order.service.OrderTrackingService;
import com.green.webstoremodels.dto.CheckoutRequest;
import com.green.webstoremodels.dto.OrderTrackingDto;
import com.green.webstoremodels.dto.OrderDto;
import com.green.webstoremodels.dto.PaymentIntentResponse;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderTrackingService orderTrackingService;

    @GetMapping
    public List<OrderDto> list() {
        return orderService.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> get(@PathVariable Integer id) {
        Order o = orderService.findById(id);
        return o != null ? ResponseEntity.ok(toDto(o)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto dto) {
        Order saved = orderService.save(toEntity(dto));
        return ResponseEntity.ok(toDto(saved));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        Order saved = orderService.checkout(checkoutRequest);
        return ResponseEntity.ok(toDto(saved));
    }

    @GetMapping("/lookup")
    public ResponseEntity<List<OrderDto>> lookup(@RequestParam String orderCode, @RequestParam String phone) {
        List<Order> orders = orderService.findByOrderCodeAndPhone(orderCode, phone);
        if (orders.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orders.stream().map(this::toDto).collect(Collectors.toList()));
    }

    @GetMapping("/by-customer/{customerId}")
    public List<OrderDto> byCustomer(@PathVariable Integer customerId) {
        return orderService.findByCustomer(customerId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}/tracking")
    public ResponseEntity<List<OrderTrackingDto>> tracking(@PathVariable Integer id) {
        Order order = orderService.findById(id);
        if (order == null) return ResponseEntity.notFound().build();
        List<OrderTrackingDto> events = orderTrackingService.findByOrder(id).stream().map(ev -> {
            OrderTrackingDto dto = new OrderTrackingDto();
            dto.setId(ev.getId());
            dto.setOrderId(id);
            dto.setStatus(ev.getStatus());
            dto.setDescription(ev.getDescription());
            dto.setLocation(ev.getLocation());
            dto.setEventTime(ev.getEventTime());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable Integer id, @RequestBody OrderDto dto) {
        Order existing = orderService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        // handle status change
        if (dto.getStatus() != null && (existing.getStatus() == null || !existing.getStatus().name().equals(dto.getStatus()))) {
            try {
                existing = orderService.updateStatus(existing, OrderStatus.valueOf(dto.getStatus()), "Status updated", null);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        existing.setAddress(dto.getAddress());
        existing.setReceiverName(dto.getReceiverName());
        existing.setReceiverPhone(dto.getReceiverPhone());
        existing.setPaymentMethod(dto.getPaymentMethod());
        existing.setTotalPrice(dto.getTotalPrice());
        existing.setOrderCode(dto.getOrderCode());
        return ResponseEntity.ok(toDto(orderService.save(existing)));
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Integer id,
                                                 @RequestParam String status,
                                                 @RequestParam(required = false) String description,
                                                 @RequestParam(required = false) String location) {
        Order order = orderService.findById(id);
        if (order == null) return ResponseEntity.notFound().build();
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status);
            Order saved = orderService.updateStatus(order, newStatus, description, location);
            return ResponseEntity.ok(toDto(saved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private OrderDto toDto(Order o) {
        OrderDto dto = new OrderDto();
        dto.setId(o.getId());
        dto.setAddress(o.getAddress());
        dto.setReceiverName(o.getReceiverName());
        dto.setReceiverPhone(o.getReceiverPhone());
        dto.setPaymentMethod(o.getPaymentMethod());
        dto.setTotalPrice(o.getTotalPrice());
        dto.setOrderCode(o.getOrderCode());
        dto.setStatus(o.getStatus() != null ? o.getStatus().name() : null);
        dto.setCustomerId(o.getCustomer() != null ? o.getCustomer().getCustomerId() : null);
        dto.setPaymentStatus(o.getPaymentStatus());
        dto.setPaymentProvider(o.getPaymentProvider());
        dto.setPaymentIntentId(o.getPaymentIntentId());
        dto.setShippingFee(o.getShippingFee());
        dto.setDiscountAmount(o.getDiscountAmount());
        dto.setVoucherCode(o.getVoucherCode());
        return dto;
    }

    private Order toEntity(OrderDto dto) {
        Order o = new Order();
        o.setId(dto.getId());
        o.setAddress(dto.getAddress());
        o.setReceiverName(dto.getReceiverName());
        o.setReceiverPhone(dto.getReceiverPhone());
        o.setPaymentMethod(dto.getPaymentMethod());
        o.setTotalPrice(dto.getTotalPrice());
        o.setOrderCode(dto.getOrderCode());
        o.setPaymentStatus(dto.getPaymentStatus());
        o.setPaymentProvider(dto.getPaymentProvider());
        o.setPaymentIntentId(dto.getPaymentIntentId());
        o.setShippingFee(dto.getShippingFee());
        o.setDiscountAmount(dto.getDiscountAmount());
        o.setVoucherCode(dto.getVoucherCode());
        if (dto.getStatus() != null) {
            o.setStatus(OrderStatus.valueOf(dto.getStatus()));
        }
        // customer mapping omitted (would require CustomerService)
        return o;
    }

    @PostMapping("/{id}/payment-intent")
    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(@PathVariable Integer id,
                                                                     @RequestParam String provider) {
        String intentId = UUID.randomUUID().toString();
        orderService.attachPaymentIntent(id, provider, intentId);
        PaymentIntentResponse resp = new PaymentIntentResponse();
        resp.setPaymentIntentId(intentId);
        resp.setProvider(provider);
        resp.setStatus("PENDING");
        resp.setRedirectUrl("https://sandbox.example/pay/" + intentId);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/payment/webhook")
    public ResponseEntity<Void> paymentWebhook(@RequestParam String intentId,
                                               @RequestParam String status) {
        orderService.handlePaymentWebhook(intentId, status);
        return ResponseEntity.noContent().build();
    }
}
