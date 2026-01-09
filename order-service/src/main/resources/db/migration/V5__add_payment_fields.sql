ALTER TABLE orders
    ADD COLUMN shipping_fee DECIMAL(15,2) DEFAULT 0 NOT NULL,
    ADD COLUMN discount_amount DECIMAL(15,2) DEFAULT 0 NOT NULL,
    ADD COLUMN voucher_code VARCHAR(64),
    ADD COLUMN payment_status VARCHAR(32) DEFAULT 'PENDING_PAYMENT',
    ADD COLUMN payment_provider VARCHAR(32),
    ADD COLUMN payment_intent_id VARCHAR(128);
