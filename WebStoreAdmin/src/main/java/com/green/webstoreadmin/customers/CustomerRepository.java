package com.green.webstoreadmin.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webstoremodels.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	@Query("SELECT c FROM Customer c WHERE c.customerId = :customerId")
	public Customer getCusById(@Param("customerId") Integer customerId);
}
