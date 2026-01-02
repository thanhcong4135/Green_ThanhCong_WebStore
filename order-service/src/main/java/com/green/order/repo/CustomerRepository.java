package com.green.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.order.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
