package com.green.webstoreadmin.users;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.User;

@Service
public class UserService {
	
	@Autowired
	public UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<User> getAllUsers(){
		return repository.findAll();
	}
	
	public User getUserByUsername(String username) {
		return repository.getByUsername(username);
	}
	
	public void deleteUserById(int id) {
		repository.deleteById(id);
	}
	
public User saveUser(User user) {
		
		Pattern bcryptPasswordPattern = Pattern.compile("^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$");  
		
		if(!bcryptPasswordPattern.matcher(user.getPassword()).matches()) {
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			
			user.setPassword(encodePassword);
		}
		
		
		repository.save(user);
		return user;
	}
	
}




