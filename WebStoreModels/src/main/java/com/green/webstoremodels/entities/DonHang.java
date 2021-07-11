package com.green.webstoremodels.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.green.webstoremodels.enumerate.TrangThaiDonHang;

@Entity
@Table(name = "donhang")
public class DonHang implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8919358350404328068L;
	
	@Id
	@Column(name = "maDH")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDH;
	
	@Column(name = "diaChi")
	private String diaChi;
	
	@Column(name = "tongTien")
	private Float tongTien;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai")
	private TrangThaiDonHang trangthai;
	
	@OneToMany(mappedBy = "donhang", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ChiTietDonHang> chitietdonhang;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private KhachHang khachhang;
	
	public Integer getMaDH() {
		return maDH;
	}

	public void setMaDH(Integer maDH) {
		this.maDH = maDH;
	}

	@Transient
	public String getTenKH() {
		return khachhang.getTenKH();
	}
	
	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public Float getTongTien() {
		return tongTien;
	}

	public void setTongTien(Float tongTien) {
		this.tongTien = tongTien;
	}

	public TrangThaiDonHang getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(TrangThaiDonHang trangthai) {
		this.trangthai = trangthai;
	}

	public Set<ChiTietDonHang> getChitietdonhang() {
		return chitietdonhang;
	}

	public void setChitietdonhang(Set<ChiTietDonHang> chitietdonhang) {
		this.chitietdonhang = chitietdonhang;
	}

	public KhachHang getKhachhang() {
		return khachhang;
	}

	public void setKhachhang(KhachHang khachhang) {
		this.khachhang = khachhang;
	}
	
	
}
