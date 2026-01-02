package com.green.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.order.domain.Order;
import com.green.order.repo.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() { return orderRepository.findAll(); }

    public Order findById(Integer id) { return orderRepository.findById(id).orElse(null); }

    public Order save(Order order) { return orderRepository.save(order); }

    public void delete(Integer id) { orderRepository.deleteById(id); }
}
