package com.green.webstoreclient.carts;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.carts.CartSessionUtil;
import com.green.webstoremodels.entities.Product;
import com.green.webstoreclient.orders.OrderService;
import com.green.webstoreclient.products.ProductService;

@Controller
public class CartController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/addtocart")
	public String byProductHandler(HttpServletRequest request, Model model, @RequestParam(value = "code", defaultValue = "") String code) {
		
		Product product = productService.getByCode(code);
		
		if (product != null) {
			CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
			cartInfo.addProduct(product, 1);
		}

		return "redirect:/shopping_cart";
	}
	
	@RequestMapping("/shopping_cart")
	public String showCartView(HttpServletRequest request, Model model) {
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);

		model.addAttribute("cartInfo", cartInfo);
		model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());
		
		return "cart";
	}
	
	@RequestMapping(value = {"/shopcart"})
	public String showHomeView(Model model) {
		
		return "cart";
	}
	
	@GetMapping("/checkout")
	public String checkoutShoppingCart(HttpServletRequest request, Model model) {
		
		CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
		model.addAttribute("orderCode", orderService.saveOrder(cartInfo));
		
		return "checkout";
	}
	
	@GetMapping("/clearcart")
	public String clearCart(HttpServletRequest request) {
		CartSessionUtil.removeCartInSession(request);
		
		return "redirect:/shopping_cart";
	}
	
	
}
