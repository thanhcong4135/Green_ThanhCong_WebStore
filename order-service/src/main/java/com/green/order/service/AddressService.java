package com.green.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.order.domain.Address;
import com.green.order.repo.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> findAll() { return addressRepository.findAll(); }

    public Address findById(Integer id) { return addressRepository.findById(id).orElse(null); }

    public Address save(Address address) { return addressRepository.save(address); }

    public void delete(Integer id) { addressRepository.deleteById(id); }
}
