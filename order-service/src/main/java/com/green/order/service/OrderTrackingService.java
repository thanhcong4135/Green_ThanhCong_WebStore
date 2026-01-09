package com.green.order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.order.domain.Order;
import com.green.order.domain.OrderTrackingEvent;
import com.green.order.repo.OrderTrackingRepository;

@Service
public class OrderTrackingService {

    @Autowired
    private OrderTrackingRepository trackingRepository;

    public List<OrderTrackingEvent> findByOrder(Integer orderId) {
        return trackingRepository.findByOrder_IdOrderByEventTimeAsc(orderId);
    }

    public OrderTrackingEvent addEvent(Order order, String status, String description, String location) {
        OrderTrackingEvent ev = new OrderTrackingEvent();
        ev.setOrder(order);
        ev.setStatus(status);
        ev.setDescription(description);
        ev.setLocation(location);
        ev.setEventTime(LocalDateTime.now());
        return trackingRepository.save(ev);
    }
}
