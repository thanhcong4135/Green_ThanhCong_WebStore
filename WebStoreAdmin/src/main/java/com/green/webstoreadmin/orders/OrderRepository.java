package com.green.webstoreadmin.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webstoremodels.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Query("SELECT o FROM Order o WHERE o.id = :id")
	public Order getOrderByid(@Param("id") int id);
}
