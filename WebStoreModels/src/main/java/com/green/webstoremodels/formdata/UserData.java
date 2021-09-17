package com.green.webstoremodels.formdata;

import java.beans.Transient;
import java.util.HashSet;
import java.util.Set;

import com.green.webstoremodels.entities.Role;
import com.green.webstoremodels.entities.User;

public class UserData {
	private Integer id;
	
	private String username;
	
	private String full_name;
	private String password;
	
	private Boolean enabled;
	
	private String avatar;
	
	private String address;
	private String phoneNumber;
	
	private String role;
	
	private Set<Role> roles = new HashSet<>();
	
public User updateUserFormData() {
		
		User user = new User();
		
		user.setId(this.id);
		user.setFull_name(this.full_name);
		user.setUsername(this.username);
		user.setPassword(this.password);
		user.setAvatar(this.avatar);
		user.setEnabled(this.enabled);
		user.setRoles(this.roles);
		user.setAddress(this.address);
		user.setPhoneNumber(this.phoneNumber);
		return user;
	}
	
	@Transient
	public void copyValueFromUserEntity(User user) {
		
		this.role = "";
		
		this.id = user.getId();
		this.full_name = user.getFull_name();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.avatar = user.getAvatar();
		this.roles = user.getRoles();

		for(Role userRole : roles) {
			this.role += userRole.getName() + ", ";
		}
		
	}
	
	@Transient
	public String getPhotoPath() {
		if(avatar == null || avatar.equals("")) {
			return "../images/avatar.jpg";
		}
		if(id != null & (avatar != null || avatar.equals(""))) {
			return "../profile-photos/" + id + "/" + avatar;
//			return "http://localhost:8081/files/" + avatar;
		}
		return null;
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


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "UserData [id=" + id + ", username=" + username + ", full_name=" + full_name + ", password=" + password
				+ ", enabled=" + enabled + ", avatar=" + avatar + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", role=" + role + ", roles=" + roles + "]";
	}
	
	
		

}
