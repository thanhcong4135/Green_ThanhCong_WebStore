package com.green.webstoreadmin.orders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webstoremodels.entities.Order;
import com.green.webstoremodels.entities.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	@Query("SELECT od FROM OrderDetail od WHERE od.id = :id")
	public OrderDetail getOrderDetaiById(@Param("id") int id);
	
	@Query("SELECT o FROM OrderDetail o WHERE o.order = :order")
	public List<OrderDetail> getByOderId(@Param("order") Order order); 
}
