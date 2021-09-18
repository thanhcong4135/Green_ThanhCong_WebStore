package com.green.webstoreadmin.users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.webstoreadmin.file.FileUploadUtil;
import com.green.webstoreadmin.handler.AppConstant;
import com.green.webstoreadmin.helper.FileUploadHelper;
import com.green.webstoreadmin.roles.RoleService;
import com.green.webstoreadmin.storage.StorageService;
import com.green.webstoremodels.entities.Product;
import com.green.webstoremodels.entities.User;
import com.green.webstoremodels.formdata.UserData;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	private final StorageService storageService;
	
	@Autowired
	public UserController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@GetMapping("/users-list")
	public String showUsers(Model model) {
	
		List<User> listUsers = userService.getAllUsers();
		
		List<UserData> copyListUser = new ArrayList<UserData>();
		
		
		for(User user : listUsers) {
			copyListUser.add(user.copyValueFromUserEntity());
		}
		
		model.addAttribute("listUsers", copyListUser);
		
		return "user_list";
	}
	
	@GetMapping("/create_user")
	public String showCreateNewUserView(Model model) {
		User user = new User();
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", roleService.getAllRoles());
		return "create_user";
	}
	
	@RequestMapping(value = "/create_user", method = RequestMethod.POST)
	public String createNewUser(@ModelAttribute("user") User user , @RequestParam("fileImage") 
								MultipartFile multipartFile, Model model) throws IOException{
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setAvatar(fileName);
		user.setEnabled(true);
		
		User saveUser = userService.saveUser(user);
		String uploadDir = "./profile-photos/" + saveUser.getId();
		System.out.println("file name: " + fileName);
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
		return "redirect:/users-list";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfileView(Model model) {
		
		User user = getCurrentUser();
		
		model.addAttribute("user", user);
		
		return "profile";
	}
	
	@Transient
	public User getCurrentUser() {
		String username = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			 username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
			
		User currentUser = userService.getUserByUsername(username);

		return currentUser;
	}
	
	@RequestMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable(name = "id") int id) {
		
		userService.deleteUserById(id);
		
		return "redirect:/users-list";
	}
	
}
