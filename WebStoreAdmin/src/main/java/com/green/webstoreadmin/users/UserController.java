package com.green.webstoreadmin.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.webstoremodels.entities.Product;
import com.green.webstoremodels.entities.User;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/users-list")
	public String showUsers(Model model) {
	
		List<User> listUsers = userService.getAllUsers();
		
		model.addAttribute("listUsers", listUsers);
		
		return "user_list";
	}
	
	@GetMapping("/add-user")
	public String showAddUserView(Model model) {
	
		User user = new User();

		model.addAttribute("user", user);
		
		return "add_user";
	}
	
}
