package com.green.webstoreadmin.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.webstoremodels.entities.Customer;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers-list")
	public String showCustomerList(Model model) {
		List<Customer> listCustomers = customerService.getAllCustomers();
		
		model.addAttribute("listCustomers", listCustomers);
		
		return "customer_list";
		
	}
}
