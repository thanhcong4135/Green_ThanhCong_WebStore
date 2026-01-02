package com.green.webstoreadmin.orders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.OrderDetailDto;
import com.green.webstoremodels.dto.OrderDto;

@Service
public class OrderDetailService {
	
	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${order.service.url:http://localhost:8092/api/orders}")
	private String orderServiceUrl;
	
	public List<OrderDetailDto> getAllOrderDetails(){
		String url = UriComponentsBuilder.fromHttpUrl(orderServiceUrl).pathSegment("details").toUriString();
		OrderDetailDto[] details = restTemplate.getForObject(url, OrderDetailDto[].class);
		return details != null ? Arrays.asList(details) : List.of();
	}
	
	public List<OrderDetailDto> getOrderDetailByOrderId(OrderDto order) {
		String url = UriComponentsBuilder.fromHttpUrl(orderServiceUrl)
				.pathSegment(String.valueOf(order.getId()), "details")
				.toUriString();
		OrderDetailDto[] details = restTemplate.getForObject(url, OrderDetailDto[].class);
		return details != null ? Arrays.asList(details) : List.of();
	}
}
