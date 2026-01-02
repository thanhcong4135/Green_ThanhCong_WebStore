package com.green.webstoreadmin.users;

import java.io.IOException;
import java.util.List;

import jakarta.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.green.webstoreadmin.file.FileUploadUtil;
import com.green.webstoreadmin.roles.RoleService;
import com.green.webstoreadmin.storage.StorageService;
import com.green.webstoremodels.dto.UserData;
import com.green.webstoremodels.dto.UserDto;

@RestController
@RequestMapping("/api/admin/users")
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
	
	@GetMapping
	public List<UserData> showUsers() {
		return userService.getAllUsers();
	}
	
	@PostMapping
	public ResponseEntity<UserDto> createNewUser(@ModelAttribute("user") UserDto user , @RequestParam(value = "fileImage", required = false) 
								MultipartFile multipartFile) throws IOException{
		
		if (multipartFile != null && !multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setAvatar(fileName);
			String uploadDir = "./profile-photos/" + user.getUsername();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		UserDto saveUser = userService.saveUser(user);
		
		return ResponseEntity.ok(saveUser);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<UserDto> showProfileView() {
		UserDto user = getCurrentUser();
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}
	
	@Transient
	public UserDto getCurrentUser() {
		String username = "";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			 username = ((UserDetails)principal).getUsername();
			} else {
			 username = principal.toString();
			}
		
		UserDto currentUser = userService.getUserByUsername(username);

		return currentUser;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") int id) {
		
		userService.deleteUserById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @ModelAttribute("user") UserDto user) {
		user.setId(id);
		UserDto saved = userService.saveUser(user);
		return ResponseEntity.ok(saved);
	}
	
}
