package com.green.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.green.order.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
