package com.green.webstoreclient.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.green.webstoremodels.dto.CustomerDto;
import com.green.webstoremodels.formdata.CustomerForm;

@RestController
@RequestMapping("/api/shop/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/profile")
	public CustomerForm showProfile(Authentication authentication) {
		
		CustomerDto customer = customerService.getCustomerByEmail(authentication.getName());
		
		return CustomerForm.getCustomerForm(customer);
	}

}
