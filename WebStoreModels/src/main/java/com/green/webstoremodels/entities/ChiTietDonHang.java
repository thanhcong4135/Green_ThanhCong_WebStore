package com.green.webstoremodels.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public class ChiTietDonHang implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9087583472885766979L;
	
	@Id
	@Column(name = "maCT")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ma;
	
	@Column(name = "tensanpham")
	private String tenSP;
	
	@Column(name = "gia")
	private Float gia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name= "maDH")
	private DonHang donhang;

	public Integer getMa() {
		return ma;
	}

	public void setMa(Integer ma) {
		this.ma = ma;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public Float getGia() {
		return gia;
	}

	public void setGia(Float gia) {
		this.gia = gia;
	}

	public DonHang getDonhang() {
		return donhang;
	}

	public void setDonhang(DonHang donhang) {
		this.donhang = donhang;
	}
	
	
	
}
