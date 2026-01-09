package com.green.webstoreclient.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.client.OrderApiClient;
import com.green.webstoreclient.customers.CustomerService;
import com.green.webstoremodels.dto.CustomerDto;
import com.green.webstoremodels.dto.OrderDto;
import com.green.webstoremodels.dto.OrderTrackingDto;

@Service
public class OrderQueryService {

    @Autowired
    private OrderApiClient orderApiClient;

    @Autowired
    private CustomerService customerService;

    public List<OrderDto> myOrders(String username) {
        CustomerDto customer = customerService.getCustomerByEmail(username);
        if (customer == null) return List.of();
        return orderApiClient.findByCustomer(customer.getId());
    }

    public List<OrderDto> lookup(String orderCode, String phone) {
        return orderApiClient.lookupOrder(orderCode, phone);
    }

    public List<OrderTrackingDto> tracking(Integer orderId) {
        return orderApiClient.getTracking(orderId);
    }
}
