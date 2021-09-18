package com.green.webstoremodels.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.green.webstoremodels.formdata.UserData;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;

	@Column(name = "full_name")
	private String full_name;
	
	@Column(name = "phone_number")
	@Size(min = 10, max = 10, message = "Invalid phone number")
	private String phoneNumber;
	
	private String password;
	private Boolean enabled;
	private String avatar;
	private String address;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
		
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Transient
	public UserData copyValueFromUserEntity() {
		
		UserData userData = new UserData();
		
		String role = "";
		
		userData.setId(this.id);
		userData.setFull_name(full_name);
		userData.setUsername(username);
		userData.setPassword(password);
		userData.setRoles(roles);;
		userData.setAvatar(avatar);
		userData.setAddress(address);
		userData.setPhoneNumber(phoneNumber);

		for(Role userRole : roles) {
			role += userRole.getName() + ", ";
		}
		
		userData.setRole(role.substring(0, role.length() - 2));
		
		return userData;
	}
	
	
	
	@Override
	public String toString() {
		return "User [avatar=" + avatar + "]";
	}

	@Transient
	public String getPhotoPath() {
		if(avatar == null  || id == null) return null;
			return "/profile-photos/" + id + "/" + avatar;
	}

}
