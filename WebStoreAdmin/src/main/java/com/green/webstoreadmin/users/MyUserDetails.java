package com.green.webstoreadmin.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.green.webstoremodels.entities.Role;
import com.green.webstoremodels.entities.User;

public class MyUserDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1388800959664940261L;
	private User user;
	
	public MyUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Set<Role> roles = user.getRoles();
		
		String message = "user : " + user.getUsername() + "has role : ";
		String messageRoles= "";
		for(Role role : roles) {
			messageRoles = messageRoles + "" + role.getName();
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		System.out.println(message + messageRoles);
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getEnabled();
	}

}
