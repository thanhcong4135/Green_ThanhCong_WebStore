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

@Entity
@Table(name = "chucvus")
public class ChucVu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "machucvu")
	private Integer ma;
	@Column(name = "ten")
	private String ten;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "nguoidung_chucvu", joinColumns = @JoinColumn(name = "machucvu"),
	inverseJoinColumns = @JoinColumn(name = "manguoidung"))
	private Set<NguoiDung> nguoidungs = new HashSet<>();

	public Integer getMa() {
		return ma;
	}

	public void setMa(Integer ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public Set<NguoiDung> getNguoidungs() {
		return nguoidungs;
	}

	public void setNguoidungs(Set<NguoiDung> nguoidungs) {
		this.nguoidungs = nguoidungs;
	}
	
}
