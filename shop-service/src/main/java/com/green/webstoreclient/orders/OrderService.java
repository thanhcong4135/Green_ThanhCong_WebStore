package com.green.webstoreclient.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.client.OrderApiClient;
import com.green.webstoreclient.customers.CustomerService;
import com.green.webstoremodels.dto.CustomerDto;
import com.green.webstoremodels.dto.OrderDto;

@Service
public class OrderService {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderApiClient orderApiClient;
	
	public String saveOrder(CartInfo cartInfo, String address) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		CustomerDto customer = customerService.getCustomerByEmail(username);

		OrderDto order = new OrderDto();
		order.setTotalPrice(cartInfo.totalCartInfo());
		order.setStatus("NEW");
		order.setOrderCode(String.valueOf(System.currentTimeMillis()));
		order.setAddress(address);
		if (customer != null) {
			order.setCustomerId(customer.getId());
		}
		OrderDto saved = orderApiClient.createOrder(order);
		return saved != null && saved.getOrderCode() != null ? saved.getOrderCode() : order.getOrderCode();
	}
	
}
