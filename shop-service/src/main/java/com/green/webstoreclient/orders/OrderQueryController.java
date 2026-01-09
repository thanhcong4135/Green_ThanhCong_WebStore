package com.green.webstoreclient.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.green.webstoreclient.client.OrderApiClient;
import com.green.webstoremodels.dto.OrderDto;
import com.green.webstoremodels.dto.OrderTrackingDto;

@RestController
@RequestMapping("/api/shop/orders")
public class OrderQueryController {

    @Autowired
    private OrderApiClient orderApiClient;
    @Autowired
    private OrderQueryService orderQueryService;

    @GetMapping("/lookup")
    public ResponseEntity<List<OrderDto>> lookup(@RequestParam String orderCode, @RequestParam String phone) {
        List<OrderDto> orders = orderApiClient.lookupOrder(orderCode, phone);
        return orders.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}/tracking")
    public List<OrderTrackingDto> tracking(@PathVariable Integer id) {
        return orderApiClient.getTracking(id);
    }

    @GetMapping("/me")
    public ResponseEntity<List<OrderDto>> myOrders() {
        var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(401).build();
        }
        String username = auth.getName();
        List<OrderDto> orders = orderQueryService.myOrders(username);
        return ResponseEntity.ok(orders);
    }
}
