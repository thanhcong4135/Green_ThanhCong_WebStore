package com.green.webstoreclient.carts;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.webstoreclient.address.AddressService;
import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.carts.CartSessionUtil;
import com.green.webstoreclient.customers.CustomerService;
import com.green.webstoremodels.entities.Address;
import com.green.webstoremodels.entities.Customer;
import com.green.webstoremodels.entities.Product;
import com.green.webstoreclient.orders.OrderService;
import com.green.webstoreclient.products.ProductService;

@Controller
public class CartController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/addtocart")
	public String byProductHandler(HttpServletRequest request, Model model, @RequestParam(value = "code", defaultValue = "") String code) {
		
		Product product = productService.getByCode(code);
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		
		if (product != null) {
			cartInfo.addProduct(product, 1);
		}
		
		model.addAttribute("cartSize", cartInfo.getCartLines().size());
		model.addAttribute("cartInfo", cartInfo);
		return "redirect:/shopping_cart";
	}
	
	@RequestMapping("/shopping_cart")
	public String showCartView(HttpServletRequest request, Model model) {
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());
		
		return "cart";
	}
	
	@RequestMapping("/mini_cart")
	public String showMiniCart(HttpServletRequest request, Model model) {
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		if(cartInfo.getCartLines() == null) {
			return "";
		}

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());
		
		return "redirect:/mini_cart";
	}
	
	@GetMapping("/checkout")
	public String checkoutShoppingCart(HttpServletRequest request, Model model) {
		
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		model.addAttribute("cartInfo", cartInfo);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		String username = userDetails.getUsername();
		Customer customer = customerService.getCustomerByEmail(username);
		
		String street;
		String city;
		Address address;

		if(addressService.getAddressByCustomerId(customer.getCustomerId()).isEmpty()) {
			model.addAttribute("address","");
			
		}else {
			street = addressService.getAddressByCustomerId(customer.getCustomerId()).get(0).getStreet();
			city = addressService.getAddressByCustomerId(customer.getCustomerId()).get(0).getCity();
			address = new Address(addressService.getAddressByCustomerId(customer.getCustomerId()).get(0).getAddressId(), street, city);
			model.addAttribute("address", address.toString());
		}
			
		model.addAttribute("username", username);
		
		
		
		return "checkout";
	}
	
	@RequestMapping(value = "/order-successful", method = RequestMethod.POST)
	public String orderSuccess(HttpServletRequest request, Model model,@RequestParam("address") String address) {
		
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("orderCode", orderService.saveOrder(cartInfo,address));
	
		CartSessionUtil.removeCartInSession(request);
		
//		return "redirect:/home";
		return "order_successful";
	}
	
	@GetMapping("/clearcart")
	public String clearCart(HttpServletRequest request) {
		CartSessionUtil.removeCartInSession(request);
		
		return "redirect:/shopping_cart";
	}
	
	
}
