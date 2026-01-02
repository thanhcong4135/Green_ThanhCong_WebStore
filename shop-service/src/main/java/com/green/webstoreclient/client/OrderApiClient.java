package com.green.webstoreclient.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.green.webstoremodels.dto.OrderDto;
import com.green.webstoremodels.dto.OrderDetailDto;
import com.green.webstoremodels.dto.CustomerDto;
import com.green.webstoremodels.dto.AddressDto;

@Component
public class OrderApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${order.service.url:http://localhost:8092/api/orders}")
    private String ordersUrl;

    @Value("${order.service.customers-url:http://localhost:8092/api/customers}")
    private String customersUrl;

    @Value("${order.service.addresses-url:http://localhost:8092/api/addresses}")
    private String addressesUrl;

    @Value("${order.service.order-details-url:http://localhost:8092/api/order-details}")
    private String orderDetailsUrl;

    public List<OrderDto> findAllOrders() {
        OrderDto[] orders = restTemplate.getForObject(ordersUrl, OrderDto[].class);
        return orders != null ? Arrays.asList(orders) : List.of();
    }

    public OrderDto findOrderById(Integer id) {
        String url = UriComponentsBuilder.fromHttpUrl(ordersUrl).pathSegment(String.valueOf(id)).toUriString();
        return restTemplate.getForObject(url, OrderDto.class);
    }

    public OrderDto createOrder(OrderDto dto) {
        return restTemplate.postForObject(ordersUrl, dto, OrderDto.class);
    }

    public List<CustomerDto> findAllCustomers() {
        CustomerDto[] customers = restTemplate.getForObject(customersUrl, CustomerDto[].class);
        return customers != null ? Arrays.asList(customers) : List.of();
    }

    public CustomerDto findCustomerById(Integer id) {
        String url = UriComponentsBuilder.fromHttpUrl(customersUrl).pathSegment(String.valueOf(id)).toUriString();
        return restTemplate.getForObject(url, CustomerDto.class);
    }

    public CustomerDto createCustomer(CustomerDto dto) {
        return restTemplate.postForObject(customersUrl, dto, CustomerDto.class);
    }

    public AddressDto createAddress(AddressDto dto) {
        return restTemplate.postForObject(addressesUrl, dto, AddressDto.class);
    }

    public List<AddressDto> findAddresses() {
        AddressDto[] addresses = restTemplate.getForObject(addressesUrl, AddressDto[].class);
        return addresses != null ? Arrays.asList(addresses) : List.of();
    }

    public List<OrderDetailDto> findOrderDetails() {
        OrderDetailDto[] details = restTemplate.getForObject(orderDetailsUrl, OrderDetailDto[].class);
        return details != null ? Arrays.asList(details) : List.of();
    }
}
