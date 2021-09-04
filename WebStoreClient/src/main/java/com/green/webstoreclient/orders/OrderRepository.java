package com.green.webstoreclient.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.green.webstoremodels.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
