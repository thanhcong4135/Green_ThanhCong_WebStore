package com.green.webstoreclient.carts;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.green.webstoreclient.address.AddressService;
import com.green.webstoreclient.customers.CustomerService;
import com.green.webstoreclient.orders.OrderService;
import com.green.webstoreclient.products.ProductService;
import com.green.webstoremodels.dto.AddressDto;
import com.green.webstoremodels.dto.CustomerDto;
import com.green.webstoremodels.dto.ProductDto;

@RestController
@RequestMapping("/api/shop/cart")
public class CartController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/items")
	public ResponseEntity<CartInfo> addProduct(HttpServletRequest request, @RequestParam(value = "code") String code) {
		
		ProductDto product = productService.getByCode(code);
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		
		if (product != null) {
			cartInfo.addProduct(product, 1);
		}
		
		return ResponseEntity.ok(cartInfo);
	}
	
	@GetMapping
	public ResponseEntity<CartInfo> getCart(HttpServletRequest request) {
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		return ResponseEntity.ok(cartInfo);
	}
	
	@GetMapping("/checkout-info")
	public ResponseEntity<?> checkoutInfo(HttpServletRequest request) {
		
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String username = userDetails.getUsername();
		CustomerDto customer = customerService.getCustomerByEmail(username);
		
		String addressStr = "";
		if(customer != null && !addressService.getAddressByCustomerId(customer.getId()).isEmpty()) {
			AddressDto addr = addressService.getAddressByCustomerId(customer.getId()).get(0);
			addressStr = addr.getStreet() + ", " + addr.getCity();
		}
			
		return ResponseEntity.ok(new CheckoutInfo(cartInfo, username, addressStr));
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<?> orderSuccess(HttpServletRequest request, @RequestParam("address") String address) {
		
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		String orderCode = orderService.saveOrder(cartInfo,address);
		CartSessionUtil.removeCartInSession(request);
		
		return ResponseEntity.ok(orderCode);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> clearCart(HttpServletRequest request) {
		CartSessionUtil.removeCartInSession(request);
		
		return ResponseEntity.noContent().build();
	}
	
	public static class CheckoutInfo {
		private CartInfo cart;
		private String username;
		private String address;
		public CheckoutInfo(CartInfo cart, String username, String address) {
			this.cart = cart; this.username = username; this.address = address;
		}
		public CartInfo getCart() { return cart; }
		public String getUsername() { return username; }
		public String getAddress() { return address; }
	}
}
