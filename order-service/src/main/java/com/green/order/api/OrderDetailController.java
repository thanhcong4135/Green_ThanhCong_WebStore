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
import org.springframework.web.bind.annotation.RestController;

import com.green.order.domain.Order;
import com.green.order.domain.OrderDetail;
import com.green.order.service.OrderDetailService;
import com.green.order.service.OrderService;
import com.green.webstoremodels.dto.OrderDetailDto;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderDetailDto> list() {
        return orderDetailService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> get(@PathVariable Integer id) {
        OrderDetail detail = orderDetailService.findById(id);
        return detail != null ? ResponseEntity.ok(toDto(detail)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OrderDetailDto> create(@RequestBody OrderDetailDto detail) {
        OrderDetail saved = orderDetailService.save(toEntity(detail, null));
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDto> update(@PathVariable Integer id, @RequestBody OrderDetailDto detail) {
        OrderDetail existing = orderDetailService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        detail.setId(id);
        OrderDetail saved = orderDetailService.save(toEntity(detail, existing));
        return ResponseEntity.ok(toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        orderDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private OrderDetailDto toDto(OrderDetail detail) {
        OrderDetailDto dto = new OrderDetailDto();
        dto.setId(detail.getId());
        dto.setProductName(detail.getProductName());
        dto.setPrice(detail.getPrice());
        dto.setQuantity(detail.getQuantity());
        dto.setOrderId(detail.getOrder() != null ? detail.getOrder().getId() : null);
        return dto;
    }

    private OrderDetail toEntity(OrderDetailDto dto, OrderDetail existing) {
        OrderDetail entity = existing != null ? existing : new OrderDetail();
        entity.setId(dto.getId());
        entity.setProductName(dto.getProductName());
        entity.setPrice(dto.getPrice());
        entity.setQuantity(dto.getQuantity());
        if (dto.getOrderId() != null) {
            Order order = orderService.findById(dto.getOrderId());
            entity.setOrder(order);
        } else if (existing == null) {
            entity.setOrder(null);
        }
        return entity;
    }
}
