package com.green.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
