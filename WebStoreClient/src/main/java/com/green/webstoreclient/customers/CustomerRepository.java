package com.green.webstoreclient.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.green.webstoremodels.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	@Query("SELECT c from Customer c WHERE c.email = :email")
	public Customer getByEmail(@Param("email") String email);
	
	@Query("SELECT c from Customer c WHERE c.phoneNumber = :phoneNumber")
	public Customer getByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
