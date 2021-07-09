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
@Table(name = "donhangs")
public class DonHang implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8919358350404328068L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "diachi")
	private String diaChi;
	
	@Column(name = "tongtien")
	private Float tongTien;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai")
	private TrangThaiDonHang trangthai;
	
	@OneToMany(mappedBy = "donhang", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ChiTietDonHang> chitietDonHangs;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private KhachHang khachhang;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Set<ChiTietDonHang> getChitietDonHangs() {
		return chitietDonHangs;
	}

	public void setChitietDonHangs(Set<ChiTietDonHang> chitietDonHangs) {
		this.chitietDonHangs = chitietDonHangs;
	}

	public KhachHang getKhachhang() {
		return khachhang;
	}

	public void setKhachhang(KhachHang khachhang) {
		this.khachhang = khachhang;
	}
	
	
}
