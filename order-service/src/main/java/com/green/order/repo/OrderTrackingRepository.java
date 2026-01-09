package com.green.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.order.domain.OrderTrackingEvent;

public interface OrderTrackingRepository extends JpaRepository<OrderTrackingEvent, Integer> {
    List<OrderTrackingEvent> findByOrder_IdOrderByEventTimeAsc(Integer orderId);
}
