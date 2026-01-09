package com.green.webstoreclient.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.client.OrderApiClient;
import com.green.webstoreclient.customers.CustomerService;
import com.green.webstoremodels.dto.CustomerDto;
import com.green.webstoremodels.dto.CheckoutItemDto;
import com.green.webstoremodels.dto.CheckoutRequest;

@Service
public class OrderService {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderApiClient orderApiClient;
	
    public String saveOrder(CartInfo cartInfo, String address, String receiverName, String receiverPhone,
                            String paymentMethod, Double shippingFee, Double discountAmount,
                            String voucherCode, String paymentProvider) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CustomerDto customer = customerService.getCustomerByEmail(username);

        CheckoutRequest order = new CheckoutRequest();
        order.setAddress(address);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order.setPaymentMethod(paymentMethod);
        order.setShippingFee(shippingFee);
        order.setDiscountAmount(discountAmount);
        order.setVoucherCode(voucherCode);
        order.setPaymentProvider(paymentProvider);
        if (customer != null) {
            order.setCustomerId(customer.getId());
        }
        if (cartInfo != null && cartInfo.getCartLines() != null) {
            order.setItems(cartInfo.getCartLines().stream().map(line -> {
				CheckoutItemDto item = new CheckoutItemDto();
				item.setProductId(line.getProduct().getId());
				item.setProductName(line.getProduct().getName());
				item.setPrice(line.getProduct().getSalePrice() != null ? line.getProduct().getSalePrice().doubleValue() : line.getProduct().getPrice().doubleValue());
				item.setQuantity(line.getQuantity());
				return item;
			}).toList());
		}
		var saved = orderApiClient.createOrder(order);
		return saved != null && saved.getOrderCode() != null ? saved.getOrderCode() : String.valueOf(System.currentTimeMillis());
	}
	
}
