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
import com.green.order.domain.OrderStatus;
import com.green.order.service.OrderService;
import com.green.webstoremodels.dto.OrderDto;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

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

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable Integer id, @RequestBody OrderDto dto) {
        Order existing = orderService.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        Order entity = toEntity(dto);
        entity.setId(id);
        return ResponseEntity.ok(toDto(orderService.save(entity)));
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
        dto.setTotalPrice(o.getTotalPrice());
        dto.setOrderCode(o.getOrderCode());
        dto.setStatus(o.getStatus() != null ? o.getStatus().name() : null);
        dto.setCustomerId(o.getCustomer() != null ? o.getCustomer().getCustomerId() : null);
        return dto;
    }

    private Order toEntity(OrderDto dto) {
        Order o = new Order();
        o.setId(dto.getId());
        o.setAddress(dto.getAddress());
        o.setTotalPrice(dto.getTotalPrice());
        o.setOrderCode(dto.getOrderCode());
        if (dto.getStatus() != null) {
            o.setStatus(OrderStatus.valueOf(dto.getStatus()));
        }
        // customer mapping omitted (would require CustomerService)
        return o;
    }
}
