package com.green.webstoreclient.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.green.webstoreclient.customers.CustomerRepository;
import com.green.webstoremodels.entities.Customer;

public class CustomerDetailsService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Customer customer = customerRepo.getByEmail(username);
		if (customer != null) {
			return new CustomerUserDetail(customer);
		}
		throw new UsernameNotFoundException("Could not find customer with email: " + username) ;
	}

}
