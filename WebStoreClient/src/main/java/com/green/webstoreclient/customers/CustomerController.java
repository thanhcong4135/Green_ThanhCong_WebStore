package com.green.webstoreclient.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.webstoremodels.entities.Customer;
import com.green.webstoremodels.formdata.CustomerForm;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
	public String showRegisterView(Model model, Authentication authentication) {
		
		Customer customer = customerService.getCustomerByEmail(authentication.getName());
		
		model.addAttribute("customerForm", CustomerForm.getCustomerForm(customer));
		
		return "profile";
	}

}
