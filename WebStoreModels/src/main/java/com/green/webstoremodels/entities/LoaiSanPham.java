package com.green.webstoremodels.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "loaisanphams")
public class LoaiSanPham {

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
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@OneToOne
	@JoinColumn(name = "nhom_id")
	private LoaiSanPham nhom;
	
	@OneToMany(mappedBy = "nhom")
	private Set<LoaiSanPham> subCategory = new HashSet<>();

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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public LoaiSanPham getNhom() {
		return nhom;
	}

	public void setNhom(LoaiSanPham nhom) {
		this.nhom = nhom;
	}

	public Set<LoaiSanPham> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<LoaiSanPham> subCategory) {
		this.subCategory = subCategory;
	}
	

}
