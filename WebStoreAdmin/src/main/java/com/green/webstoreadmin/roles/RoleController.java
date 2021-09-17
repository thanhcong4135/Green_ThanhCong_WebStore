package com.green.webstoreadmin.roles;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.webstoremodels.entities.Product;
import com.green.webstoremodels.entities.Role;

@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("/roles-list")
	public String showRoleView(Model model) {
		List<Role> listRole = roleService.getAllRoles();
		
		model.addAttribute("listRoles", listRole);
		
		return "role_list";
	}
	
	@RequestMapping(value = "/edit_role/{id}", method = RequestMethod.GET)
	public String showEditRoleView(@PathVariable(name = "id") Integer id, Model model) {
		
		Role role = roleService.getRoleById(id);
		
		model.addAttribute("role", role);
		
		return "edit_role";
		
	}
	
	@RequestMapping(value = "/edit_role", method = RequestMethod.POST)
	public String checkExistRole(@ModelAttribute("role") Role role) {
		
		roleService.save(role);
		
		return "redirect:/roles-list";
	}
	
}
