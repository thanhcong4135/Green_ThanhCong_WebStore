package com.green.webstoreclient.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.green.webstoremodels.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	@Query("SELECT a from Address a WHERE a.addressId = :id")
	public Address getById(@Param("id") String id);
	
	@Query("SELECT a from Address a WHERE a.customer.customerId = :cusid")
	public List<Address> getAddressByCusId(@Param("cusid") int id);
	
}
