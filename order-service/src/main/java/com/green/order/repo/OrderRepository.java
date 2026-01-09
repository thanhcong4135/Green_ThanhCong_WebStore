package com.green.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.orderCode = :orderCode AND o.customer.phoneNumber = :phone")
    List<Order> findByOrderCodeAndPhone(@Param("orderCode") String orderCode, @Param("phone") String phone);

    List<Order> findByCustomer_CustomerId(Integer customerId);

    Optional<Order> findByPaymentIntentId(String paymentIntentId);
}
