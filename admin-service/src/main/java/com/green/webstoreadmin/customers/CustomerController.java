package com.green.webstoreadmin.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.webstoremodels.dto.CustomerDto;

@RestController
@RequestMapping("/api/admin/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public List<CustomerDto> showCustomerList() {
		return customerService.getAllCustomers();
	}
}
