package com.green.webstoreadmin.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.Customer;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository repository;
	
	public List<Customer> getAllCustomers() {
		return repository.findAll();
	}
	
	public Customer getCustomerById(Integer customerId) {
		return repository.getCusById(customerId);
	}
	
	public void saveCustomer(Customer customer) {
		repository.save(customer);
	}
	
	public void deleteCustomerById(Integer id) {
		repository.deleteById(id);
	}
}
