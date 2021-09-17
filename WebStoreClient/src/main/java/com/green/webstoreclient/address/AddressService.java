package com.green.webstoreclient.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.Address;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	public void saveAddress(Address a) {
		addressRepository.save(a);
	}
	
	public void updateAddress(Address a,int id) {
		Address address = addressRepository.getById(id);
		address.setCity(a.getCity());
		address.setStreet(a.getStreet());
		addressRepository.save(address);
	}
	
	public List<Address> getAddressByCustomerId(int id) {
		
		return addressRepository.getAddressByCusId(id);
		
	}

}
