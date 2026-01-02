package com.green.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.order.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
