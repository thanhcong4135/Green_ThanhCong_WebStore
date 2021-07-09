package com.green.webstoremodels.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sanphams")
public class SanPham {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ten")
	private String ten;
	
	@Column(name = "ma")
	private String ma;
	
	@Column(name = "mota")
	private String moTa;
	
	@Column(name = "hinhanh")
	private String hinhAnh;
	
	@Column(name = "gia")
	private Float gia;
	
	@Column(name = "giasale")
	private Float giaSale;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@ManyToOne
	@JoinColumn(name = "loaisanpham_id")
	private LoaiSanPham loaisanpham;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public Float getGia() {
		return gia;
	}

	public void setGia(Float gia) {
		this.gia = gia;
	}

	public Float getGiaSale() {
		return giaSale;
	}

	public void setGiaSale(Float giaSale) {
		this.giaSale = giaSale;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public LoaiSanPham getLoaisanpham() {
		return loaisanpham;
	}

	public void setLoaisanpham(LoaiSanPham loaisanpham) {
		this.loaisanpham = loaisanpham;
	}
	
	
}
