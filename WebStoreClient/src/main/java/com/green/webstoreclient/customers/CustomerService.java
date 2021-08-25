package com.green.webstoreclient.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.Customer;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository repository;
	
	public void saveCustomer(Customer c) {
		repository.save(c);
	}
	
	public Customer getCustomerByPhone(String phoneNumber) {
		return repository.getByPhoneNumber(phoneNumber);
	}
	
	public Customer getCustomerByEmail(String email) {
		return repository.getByEmail(email);
	}
}
