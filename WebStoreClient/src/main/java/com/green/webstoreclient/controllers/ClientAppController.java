package com.green.webstoreclient.controllers;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;

import com.green.webstoreclient.securities.PasswordManager;
import com.green.webstoremodels.formdata.CustomerForm;
import com.green.webstoreclient.helpers.EmailServiceImpl;
import com.green.webstoremodels.entities.Customer;
import com.green.webstoreclient.carts.CartInfo;
import com.green.webstoreclient.carts.CartSessionUtil;
import com.green.webstoreclient.customers.CustomerService;

@Controller
public class ClientAppController {
	 @Autowired
	    private TemplateEngine htmlTemplateEngine;
	    
		@Autowired
		private CustomerService customerService;
		
		@Autowired
		private JavaMailSender javaMailSender; 
		
		@GetMapping("/login")
		public String showLoginView() {
			return "login";
		}
		
		@RequestMapping(value = {"/", "/home"})
		public String showHomeView(HttpServletRequest request,Model model) {
			CartInfo cartInfo = CartSessionUtil.getCartInSession(request);

			model.addAttribute("cartInfo", cartInfo);
			model.addAttribute("totalCartInfo", cartInfo.totalCartInfo());
			model.addAttribute("cartSize", cartInfo.getCartLines().size());
			
			System.out.println("return home view !");
			//System.out.println(PasswordManager.getBCrypPassword("12345678"));
			
			return "index";
		}
		
		@RequestMapping(value = {"/register"}, method = RequestMethod.GET)
		public String showRegisterView(Model model) {
			
			CustomerForm customerForm = new CustomerForm();
			
			model.addAttribute("customerForm", customerForm);
			
			return "register";
		}
		
		@RequestMapping(value = "/register", method = RequestMethod.POST)
		public String checkCustomerInfo(@Valid CustomerForm customerForm, BindingResult bindingResult, RedirectAttributes redirectAttributes)   {
			
			boolean error = false;
			
			Customer customer = customerService.getCustomerByPhone(customerForm.getPhoneNumber());
			Customer customer2 = customerService.getCustomerByEmail(customerForm.getEmail());
			
			if(customer2 != null) {
				redirectAttributes.addFlashAttribute("error_duplicate_email", "the email used by another one");
				error=true;
			}
			
			if (customer != null) {
				redirectAttributes.addFlashAttribute("error_duplicate_phone", "the phone number used by another one");
				error=true;
			}
			
			if(error) {
				return "redirect:/register";
			}
			
			System.out.println("checkCustomerInfo: " + customerForm.toString());
			
			if (bindingResult.hasErrors()) {
				return "register";
			}
			
			
			
			//register new account;
			customerService.saveCustomer(customerForm.getCustomer());
	
			try {
				EmailServiceImpl.sendSimpleMail(javaMailSender, htmlTemplateEngine , "Cong", "thanhcong4135@gmail.com" , Locale.getDefault());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return "redirect:/";
		}

}
