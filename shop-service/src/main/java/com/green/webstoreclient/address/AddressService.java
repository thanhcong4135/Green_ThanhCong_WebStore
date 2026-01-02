package com.green.webstoreclient.address;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.client.OrderApiClient;
import com.green.webstoremodels.dto.AddressDto;

@Service
public class AddressService {
	
	@Autowired
	private OrderApiClient orderApiClient;
	
	public AddressDto saveAddress(AddressDto a) {
		return orderApiClient.createAddress(a);
	}
	
	public AddressDto updateAddress(AddressDto a,int id) {
		// order-service chưa có update riêng, tạm xoá + tạo mới hoặc bỏ qua
		// ở đây trả về địa chỉ hiện tại nếu tìm thấy
		return getAddressById(id);
	}
	
	public List<AddressDto> getAddressByCustomerId(int id) {
		return orderApiClient.findAddresses().stream()
				.filter(addr -> addr.getCustomerId() != null && addr.getCustomerId().equals(id))
				.collect(Collectors.toList());
	}

	public AddressDto getAddressById(Integer id) {
		return orderApiClient.findAddresses().stream()
				.filter(addr -> addr.getId() != null && addr.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

}
