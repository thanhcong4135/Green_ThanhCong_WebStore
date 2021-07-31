package com.green.webstoreadmin.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.webstoremodels.entities.User;

@Service
public class UserService {
	
	@Autowired
	public UserRepository repository;
	
	public List<User> getAllUsers(){
		return repository.findAll();
	}
	
	public User getUserByUsername(String username) {
		return repository.getByUsername(username);
	}
	
	public void deleteUserById(Integer id) {
		repository.deleteById(id);
	}
	
}




