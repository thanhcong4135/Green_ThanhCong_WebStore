package com.green.webstoreadmin.orders;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.OrderDto;

@Service
public class OrderService {
	private final RestTemplate restTemplate = new RestTemplate();

	@Value("${order.service.url:http://localhost:8092/api/orders}")
	private String orderServiceUrl;
	
	public List<OrderDto> getAllOrders() {
		OrderDto[] orders = restTemplate.getForObject(orderServiceUrl, OrderDto[].class);
		return orders != null ? Arrays.asList(orders) : List.of();
	}
	
	public OrderDto getOrderById(int id) {
		String url = UriComponentsBuilder.fromHttpUrl(orderServiceUrl).pathSegment(String.valueOf(id)).toUriString();
		return restTemplate.getForObject(url, OrderDto.class);
	}
}
