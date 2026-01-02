package com.green.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.order.domain.Customer;
import com.green.order.repo.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() { return customerRepository.findAll(); }

    public Customer findById(Integer id) { return customerRepository.findById(id).orElse(null); }

    public Customer save(Customer customer) { return customerRepository.save(customer); }

    public void delete(Integer id) { customerRepository.deleteById(id); }
}
