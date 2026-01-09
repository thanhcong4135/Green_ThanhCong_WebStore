package com.green.webstoreclient.carts;

import java.time.Instant;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CartTokenService {

    private static class CartEntry {
        CartInfo cart;
        Instant lastAccess;
    }

    private final Map<String, CartEntry> carts = new ConcurrentHashMap<>();

    @Value("${shop.cart.ttl-minutes:120}")
    private long ttlMinutes;

    public CartWithId getOrCreate(String cartId) {
        cleanupExpired();
        CartEntry entry = carts.get(cartId);
        if (entry == null) {
            entry = new CartEntry();
            entry.cart = new CartInfo();
            entry.lastAccess = Instant.now();
            cartId = UUID.randomUUID().toString();
            carts.put(cartId, entry);
        } else {
            entry.lastAccess = Instant.now();
        }
        return new CartWithId(cartId, entry.cart);
    }

    public void remove(String cartId) {
        carts.remove(cartId);
    }

    private void cleanupExpired() {
        Instant now = Instant.now();
        Duration ttl = Duration.ofMinutes(ttlMinutes);
        carts.entrySet().removeIf(e -> e.getValue().lastAccess.plus(ttl).isBefore(now));
    }

    public static class CartWithId {
        private final String cartId;
        private final CartInfo cart;
        public CartWithId(String cartId, CartInfo cart) {
            this.cartId = cartId;
            this.cart = cart;
        }
        public String getCartId() { return cartId; }
        public CartInfo getCart() { return cart; }
    }
}
