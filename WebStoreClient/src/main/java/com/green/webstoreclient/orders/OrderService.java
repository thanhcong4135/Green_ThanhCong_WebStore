package com.green.webstoreclient.orders;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.carts.CartLineInfo;
import com.green.webstoremodels.entities.Order;
import com.green.webstoremodels.entities.OrderDetail;
import com.green.webstoremodels.enumerate.OrderStatus;
import com.green.webstoreclient.customers.CustomerService;

@Service
public class OrderService {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	OrderRepository orderRepository;
	
	public String saveOrder(CartInfo cartInfo, String address) {
		
		Order order = new Order();
		
		Set<OrderDetail> listOrderDetail = new HashSet<OrderDetail>();
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
        Authentication authentication = securityContext.getAuthentication();
		
		order.setTotalPrice(cartInfo.totalCartInfo());
		
		order.setCustomer(customerService.getCustomerByEmail(authentication.getName()));
		
		for(CartLineInfo cartLineInfo : cartInfo.getCartLines()) {
			
			OrderDetail orderDetail = new OrderDetail();
			
			orderDetail.setOrder(order);
			
			orderDetail.setPrice(cartLineInfo.getTotal());
			
			System.out.println(cartLineInfo.getQuantity());
			
			orderDetail.setQuantity(cartLineInfo.getQuantity());
			
			orderDetail.setProductName(cartLineInfo.getProduct().getName());
			
			listOrderDetail.add(orderDetail);
		}
		
		order.setOrderDetails(listOrderDetail);
		
		order.setStatus(OrderStatus.NEW);
		
		order.setOrderCode(System.currentTimeMillis() + "");
		
		order.setAddress(address);
		
		orderRepository.save(order);
		
		return order.getOrderCode();
	}
	
}
