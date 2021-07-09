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
@Table(name = "khachhang")
public class KhachHang {
	
	@Id
	@Column(name = "maKH", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maKH;
	
	@Column(name = "tenKH", unique = false, nullable = false, length = 100)
	private String tenKH;

	@Column(name = "emailKH", unique = true, nullable = false, length = 64)
	private String emailKH;
	
	@Column(name = "passWord", unique = false, nullable = false, length = 128)
	private String passWord;
	
	@Column(name = "soDT", unique = false, length = 11)
	private String soDT;

	@Column(name = "verified",columnDefinition = "tinyint(1) default 0")
	private boolean verified;
	
	@Column(name = "verificationCode", unique = false, length = 128)
	private String verificationCode;

	public Integer getMaKH() {
		return maKH;
	}

	public void setMaKH(Integer maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public String getEmailKH() {
		return emailKH;
	}

	public void setEmailKH(String emailKH) {
		this.emailKH = emailKH;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
//	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private Set<Address> addresses = new HashSet<>();
//	
//	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private Set<Order> orders = new HashSet<>();

	
}
