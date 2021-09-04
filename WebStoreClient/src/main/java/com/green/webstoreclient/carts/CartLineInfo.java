package com.green.webstoreclient.carts;

import com.green.webstoremodels.entities.Product;

public class CartLineInfo {

	private Product product;
	private int quantity;
	private double unitPrice;
	private double total;

	public CartLineInfo() {
		this.quantity = 0;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		this.unitPrice = product.getPrice();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotal() {
		return unitPrice * quantity;
	}

}
