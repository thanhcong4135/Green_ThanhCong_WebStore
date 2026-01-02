package com.green.webstoremodels.formdata;

import java.util.HashSet;
import java.util.Set;

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
	private Set<String> roles = new HashSet<>();

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getFull_name() { return full_name; }
	public void setFull_name(String full_name) { this.full_name = full_name; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public Boolean getEnabled() { return enabled; }
	public void setEnabled(Boolean enabled) { this.enabled = enabled; }
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
	public Set<String> getRoles() { return roles; }
	public void setRoles(Set<String> roles) { this.roles = roles; }
	public String getAvatar() { return avatar; }
	public void setAvatar(String avatar) { this.avatar = avatar; }
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
