package com.green.webstoreadmin.orders;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.webstoremodels.dto.OrderDto;
import com.green.webstoremodels.dto.OrderDetailDto;

@RestController
@RequestMapping("/api/admin/orders")
public class OrderDetailController {
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public List<OrderDto> showOrders() {
		return orderService.getAllOrders();
	}
	
	@GetMapping("/{id}/details")
	public ResponseEntity<List<OrderDetailDto>> detailOrder(@PathVariable(name = "id") int id) {
		OrderDto order = orderService.getOrderById(id);
		if (order == null) return ResponseEntity.notFound().build();
		List<OrderDetailDto> listOrderDetails = new ArrayList<OrderDetailDto>();
		
		listOrderDetails = orderDetailService.getOrderDetailByOrderId(order);

		return ResponseEntity.ok(listOrderDetails);
		
	}

}
