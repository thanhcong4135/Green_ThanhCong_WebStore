package com.green.webstoreclient.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.client.OrderApiClient;
import com.green.webstoreclient.securities.PasswordManager;
import com.green.webstoremodels.dto.CustomerDto;

@Service
public class CustomerService {
	
	@Autowired
	private OrderApiClient orderApiClient;
	
	public CustomerDto saveCustomer(CustomerDto c) {
		if (c.getPassword() != null) {
			String encodePassword = PasswordManager.getBCrypPassword(c.getPassword());
			c.setPassword(encodePassword);
		}
		return orderApiClient.createCustomer(c);
	}
	
	public CustomerDto getCustomerByPhone(String phoneNumber) {
		List<CustomerDto> customers = orderApiClient.findAllCustomers();
		return customers.stream()
				.filter(c -> c.getPhoneNumber() != null && c.getPhoneNumber().equals(phoneNumber))
				.findFirst()
				.orElse(null);
	}
	
	public CustomerDto getCustomerByEmail(String email) {
		List<CustomerDto> customers = orderApiClient.findAllCustomers();
		return customers.stream()
				.filter(c -> c.getEmail() != null && c.getEmail().equalsIgnoreCase(email))
				.findFirst()
				.orElse(null);
	}
}
