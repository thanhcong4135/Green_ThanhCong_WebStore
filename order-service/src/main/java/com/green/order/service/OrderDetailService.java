package com.green.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.order.domain.OrderDetail;
import com.green.order.repo.OrderDetailRepository;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> findAll() { return orderDetailRepository.findAll(); }

    public OrderDetail findById(Integer id) { return orderDetailRepository.findById(id).orElse(null); }

    public OrderDetail save(OrderDetail detail) { return orderDetailRepository.save(detail); }

    public void delete(Integer id) { orderDetailRepository.deleteById(id); }
}
