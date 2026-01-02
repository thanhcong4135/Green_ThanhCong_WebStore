package com.green.webstoreclient.controllers;

import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.javamail.JavaMailSender;

import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.carts.CartSessionUtil;
import com.green.webstoreclient.customers.CustomerService;
import com.green.webstoreclient.helpers.EmailServiceImpl;
import com.green.webstoremodels.dto.CustomerDto;
import com.green.webstoremodels.formdata.CustomerForm;

@RestController
@RequestMapping("/api/shop")
public class ClientAppController {
		@Autowired
		private CustomerService customerService;
		
		@Autowired
		private JavaMailSender javaMailSender; 
		
		@GetMapping("/login")
		public ResponseEntity<String> showLoginView() {
			return ResponseEntity.ok("login endpoint - handled by frontend");
		}
		
		@RequestMapping(value = {"/home"})
		public ResponseEntity<CartInfo> showHomeView(HttpServletRequest request) {
			CartInfo cartInfo = CartSessionUtil.getCartInSession(request);
			return ResponseEntity.ok(cartInfo);
		}
		
		@RequestMapping(value = {"/register"}, method = RequestMethod.GET)
		public ResponseEntity<CustomerForm> showRegisterView() {
			return ResponseEntity.ok(new CustomerForm());
		}
		
		@RequestMapping(value = "/register", method = RequestMethod.POST)
		public ResponseEntity<?> checkCustomerInfo(@Valid CustomerForm customerForm, BindingResult bindingResult)   {
			
			CustomerDto customer = customerService.getCustomerByPhone(customerForm.getPhoneNumber());
			CustomerDto customer2 = customerService.getCustomerByEmail(customerForm.getEmail());
			
			if(customer2 != null) {
				return ResponseEntity.badRequest().body("the email used by another one");
			}
			
			if (customer != null) {
				return ResponseEntity.badRequest().body("the phone number used by another one");
			}
			
			if (bindingResult.hasErrors()) {
				return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
			}
			
			CustomerDto newCustomer = new CustomerDto();
			newCustomer.setEmail(customerForm.getEmail());
			newCustomer.setPassword(customerForm.getPassword());
			newCustomer.setPhoneNumber(customerForm.getPhoneNumber());
			newCustomer.setFirstName(customerForm.getName());
			newCustomer.setLastName("");
			
			//register new account;
			customerService.saveCustomer(newCustomer);
	
			EmailServiceImpl.sendSimpleMail(javaMailSender, "Cong", "thanhcong4135@gmail.com" , Locale.getDefault());
			return ResponseEntity.ok("registered");
		}

}
