package com.green.webstoreclient.carts;

public class CartResponse {
    private String cartId;
    private CartInfo cart;

    public CartResponse(String cartId, CartInfo cart) {
        this.cartId = cartId;
        this.cart = cart;
    }

    public String getCartId() { return cartId; }
    public CartInfo getCart() { return cart; }
}
