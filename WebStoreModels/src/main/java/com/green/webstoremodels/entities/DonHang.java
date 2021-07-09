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

import com.green.webstoremodels.enumerate.TrangThaiDonHang;

@Entity
@Table(name = "donhang")
public class DonHang implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8919358350404328068L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "TOTAL_PRICE")
	private Float totalPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai")
	private TrangThaiDonHang trangthai;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//private Set<OrderDetail> orderDetails;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private KhachHang khachhang;
}
