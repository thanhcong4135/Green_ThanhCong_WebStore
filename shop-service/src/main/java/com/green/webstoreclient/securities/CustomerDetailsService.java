package com.green.webstoreclient.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.green.webstoreclient.customers.CustomerService;
import com.green.webstoremodels.dto.CustomerDto;

public class CustomerDetailsService implements UserDetailsService {

	@Autowired
	private CustomerService customerService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		CustomerDto customer = customerService.getCustomerByEmail(username);
		if (customer != null) {
			return new CustomerUserDetail(customer);
		}
		throw new UsernameNotFoundException("Could not find customer with email: " + username) ;
	}

}
