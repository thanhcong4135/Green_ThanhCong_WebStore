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

	@Autowired
	private CartTokenService cartTokenService;
	
	@PostMapping("/items")
	public ResponseEntity<CartResponse> addProduct(@RequestParam(value = "code") String code,
			@RequestParam(value = "cartId", required = false) String cartId) {
		
		ProductDto product = productService.getByCode(code);
		var cartWithId = cartTokenService.getOrCreate(cartId);
		if (product != null) {
			cartWithId.getCart().addProduct(product, 1);
		}
		
		return ResponseEntity.ok(new CartResponse(cartWithId.getCartId(), cartWithId.getCart()));
	}
	
	@GetMapping
	public ResponseEntity<CartResponse> getCart(@RequestParam(value = "cartId", required = false) String cartId) {
		var cartWithId = cartTokenService.getOrCreate(cartId);
		return ResponseEntity.ok(new CartResponse(cartWithId.getCartId(), cartWithId.getCart()));
	}
	
	@GetMapping("/checkout-info")
	public ResponseEntity<?> checkoutInfo(@RequestParam(value = "cartId", required = false) String cartId) {
		var cartWithId = cartTokenService.getOrCreate(cartId);
		CartInfo cartInfo = cartWithId.getCart();

		String username = null;
		CustomerDto customer = null;
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
			customer = customerService.getCustomerByEmail(username);
		} catch (Exception ignored) {}
		
		String addressStr = "";
		if(customer != null && !addressService.getAddressByCustomerId(customer.getId()).isEmpty()) {
			AddressDto addr = addressService.getAddressByCustomerId(customer.getId()).get(0);
			addressStr = addr.getStreet() + ", " + addr.getCity();
		}
			
		return ResponseEntity.ok(new CheckoutInfo(cartWithId.getCartId(), cartInfo, username, addressStr));
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<?> orderSuccess(
			@RequestParam("address") String address,
			@RequestParam(value = "receiverName", required = false) String receiverName,
			@RequestParam(value = "receiverPhone", required = false) String receiverPhone,
			@RequestParam(value = "paymentMethod", required = false) String paymentMethod,
			@RequestParam(value = "shippingFee", required = false) Double shippingFee,
			@RequestParam(value = "discountAmount", required = false) Double discountAmount,
			@RequestParam(value = "voucherCode", required = false) String voucherCode,
			@RequestParam(value = "paymentProvider", required = false) String paymentProvider,
			@RequestParam(value = "cartId", required = false) String cartId) {
		
		var cartWithId = cartTokenService.getOrCreate(cartId);
		CartInfo cartInfo = cartWithId.getCart();
		String orderCode = orderService.saveOrder(
				cartInfo,
				address,
				receiverName,
				receiverPhone,
				paymentMethod,
				shippingFee,
				discountAmount,
				voucherCode,
				paymentProvider);
		if (cartWithId.getCartId() != null) {
			cartTokenService.remove(cartWithId.getCartId());
		}
		return ResponseEntity.ok(orderCode);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> clearCart(@RequestParam(value = "cartId", required = false) String cartId) {
		if (cartId != null) {
			cartTokenService.remove(cartId);
		}
		
		return ResponseEntity.noContent().build();
	}
	
	public static class CheckoutInfo {
		private String cartId;
		private CartInfo cart;
		private String username;
		private String address;
		public CheckoutInfo(String cartId, CartInfo cart, String username, String address) {
			this.cartId = cartId;
			this.cart = cart; this.username = username; this.address = address;
		}
		public String getCartId() { return cartId; }
		public CartInfo getCart() { return cart; }
		public String getUsername() { return username; }
		public String getAddress() { return address; }
	}
}
