package com.green.webstoreclient.carts;

import java.util.ArrayList;
import java.util.List;

import com.green.webstoremodels.entities.Product;

public class CartInfo {

	private ShipInfo shipInfo;

	private List<CartLineInfo> cartLines = new ArrayList<>();

	public ShipInfo getShipInfo() {
		return shipInfo;
	}

	public void setShipInfo(ShipInfo shipInfo) {
		this.shipInfo = shipInfo;
	}

	public List<CartLineInfo> getCartLines() {
		return cartLines;
	}

	public void setCartLines(List<CartLineInfo> cartLines) {
		this.cartLines = cartLines;
	}

	public void addProduct(Product product, int quantity) {
		if (quantity <= 0) {
			return;
		}
		CartLineInfo cartLineInfo = findCartLineProduct(product.getCode());

		if (cartLineInfo == null) {
			cartLineInfo = new CartLineInfo();
			cartLineInfo.setProduct(product);
			cartLineInfo.setQuantity(quantity);
			cartLines.add(cartLineInfo);
		} else {
			int newQuantity = cartLineInfo.getQuantity() + quantity;
			cartLineInfo.setQuantity(newQuantity);
		}
	}

	public void removeProduct(Product product) {
		CartLineInfo cartLineInfo = findCartLineProduct(product.getCode());

		if (cartLineInfo != null) {
			cartLines.remove(cartLineInfo);
		}
	}

	public void updateProduct(Product product, int quantity) {
		CartLineInfo cartLineInfo = findCartLineProduct(product.getCode());

		if (cartLineInfo != null) {
			cartLineInfo.setQuantity(quantity);
		}
	}

	public boolean isEmpty() {
		return cartLines.isEmpty();
	}

	private CartLineInfo findCartLineProduct(String code) {
		for (CartLineInfo line : cartLines) {
			if (line.getProduct().getCode().equals(code)) {
				return line;
			}
		}
		return null;
	}

	public double totalCartInfo() {
		double sum = 0;
		for (CartLineInfo line : cartLines) {
			sum = sum + line.getTotal();

		}
		return sum;
	}
}
