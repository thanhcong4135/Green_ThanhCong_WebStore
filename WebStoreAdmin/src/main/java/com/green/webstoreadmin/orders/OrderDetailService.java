package com.green.webstoreadmin.orders;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.Order;
import com.green.webstoremodels.entities.OrderDetail;

@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	public List<OrderDetail> getAllOrderDetails(){
		return orderDetailRepository.findAll();
	}
	
	public OrderDetail save(OrderDetail orderDetail) {
		return orderDetailRepository.save(orderDetail);
	}
	
	public List<OrderDetail> getOrderDetailByOrderId(Order order) {
		 return orderDetailRepository.getByOderId(order);
	}
	
	
	

}
