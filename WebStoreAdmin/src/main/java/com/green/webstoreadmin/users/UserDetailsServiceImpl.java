package com.green.webstoreadmin.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.green.webstoreadmin.helper.PasswordManager;
import com.green.webstoremodels.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getByUsername(username);
		
//		String password = PasswordManager.getBCrypPassword("123456");
//		System.out.println(password);
		
		if(user == null) {
			throw new UsernameNotFoundException("Ten dang nhap khong ton tai");
		}
		return new MyUserDetails(user);
	}

}
