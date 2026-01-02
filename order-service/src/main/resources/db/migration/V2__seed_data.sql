-- bcrypt hash for "password"
INSERT INTO customers (customerId, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, phone_number, verified) VALUES
    (1, 'John', 'Doe', 'john@example.com', '$2a$10$2b2BP3ennLcpkI7aI3d7sesC3tt0VDeCy9lrVCUJqs0edx/FrQFZS', '0900000001', 1),
    (2, 'Jane', 'Smith', 'jane@example.com', '$2a$10$2b2BP3ennLcpkI7aI3d7sesC3tt0VDeCy9lrVCUJqs0edx/FrQFZS', '0900000002', 1);

INSERT INTO address (ID, STREET, CITY, customerId) VALUES
    (1, '123 Main St', 'Hanoi', 1),
    (2, '456 Market St', 'Saigon', 2);

INSERT INTO orders (id, ADDRESS, TOTAL_PRICE, orderCode, STATUS, customer_customerId) VALUES
    (1, '123 Main St, Hanoi', 2149.98, 'ORD-1001', 'NEW', 1),
    (2, '456 Market St, Saigon', 749.00, 'ORD-1002', 'NEW', 2);

INSERT INTO orderdetail (PRODUCT_NAME, PRICE, quantity, order_id) VALUES
    ('iPhone 15 Pro', 1150.00, 1, 1),
    ('Galaxy S24', 999.98, 1, 1),
    ('Pixel 8', 749.00, 1, 2);
