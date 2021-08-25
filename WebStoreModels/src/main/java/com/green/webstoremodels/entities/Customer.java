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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {

	@Id
	@Column(name = "customerId", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;

	@Column(name = "FIRST_NAME", unique = false, nullable = false, length = 100)
	private String firstName;

	@Column(name = "LAST_NAME", unique = false, nullable = false, length = 100)
	private String lastName;

	@Column(name = "EMAIL", unique = true, nullable = false, length = 64)
	private String email;

	@Column(name = "PASSWORD", unique = false, nullable = false, length = 128)
	private String password;
	
	@Column(name = "phone_number", unique = true, length = 12)
	private String phoneNumber;
	
	@Column(name = "verified" , columnDefinition = "tinyint(1) default 0")
	private Boolean verified;
	
	@Column(name = "verification_code", unique = true, length = 128)
	private String verificationCode;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Address> addresses = new HashSet<>();
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Order> orders = new HashSet<>();

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	
}
