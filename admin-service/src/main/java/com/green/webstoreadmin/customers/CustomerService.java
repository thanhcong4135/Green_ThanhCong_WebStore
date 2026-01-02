package com.green.webstoreadmin.customers;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.CustomerDto;

@Service
public class CustomerService {
	
	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${order.service.url:http://localhost:8092/api/customers}")
	private String orderServiceUrl;
	
	public List<CustomerDto> getAllCustomers() {
		CustomerDto[] customers = restTemplate.getForObject(orderServiceUrl, CustomerDto[].class);
		return customers != null ? Arrays.asList(customers) : List.of();
	}
	
	public CustomerDto getCustomerById(Integer customerId) {
		String url = UriComponentsBuilder.fromHttpUrl(orderServiceUrl).pathSegment(String.valueOf(customerId)).toUriString();
		return restTemplate.getForObject(url, CustomerDto.class);
	}
	
	public void saveCustomer(CustomerDto customer) {
		if (customer.getId() == null) {
			restTemplate.postForEntity(orderServiceUrl, customer, Void.class);
		} else {
			String url = UriComponentsBuilder.fromHttpUrl(orderServiceUrl).pathSegment(String.valueOf(customer.getId())).toUriString();
			restTemplate.put(url, customer);
		}
	}
	
	public void deleteCustomerById(Integer id) {
		String url = UriComponentsBuilder.fromHttpUrl(orderServiceUrl).pathSegment(String.valueOf(id)).toUriString();
		restTemplate.delete(url);
	}
}
