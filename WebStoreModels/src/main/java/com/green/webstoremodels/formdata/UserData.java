package com.green.webstoremodels.formdata;

import java.util.Set;

import com.green.webstoremodels.entities.Role;
import com.green.webstoremodels.entities.User;

public class UserData {
	private Integer id;
	
	private String username;
	
	private String full_name;
	private String password;
	
	private Boolean enabled;
	
	private Set<Role> role;
	
	public static UserData copyValueFormEntity(User entity) {
		UserData data = new UserData();
		
		data.id = entity.getId();
		data.username = entity.getUsername();
		data.full_name = entity.getFull_name();
		data.password = entity.getPassword();
		data.enabled = entity.getEnabled();
		data.role = entity.getRoles();
		
		return data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
	
		

}
