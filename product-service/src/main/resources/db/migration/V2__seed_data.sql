INSERT INTO categories (id, name, code, description, enabled) VALUES
    (1, 'Smartphones', 'smartphones', 'Phones from major brands', 1),
    (2, 'Accessories', 'accessories', 'Phone accessories', 1);

INSERT INTO products (name, code, description, photo, price, sale_price, enabled, category_id, brand_name) VALUES
    ('iPhone 15 Pro', 'iphone-15-pro', 'Apple flagship', 'iphone15pro.jpg', 1200.00, 1150.00, 1, 1, 'Apple'),
    ('Galaxy S24', 'galaxy-s24', 'Samsung flagship', 'galaxys24.jpg', 999.99, 949.99, 1, 1, 'Samsung'),
    ('Pixel 8', 'pixel-8', 'Google Pixel', 'pixel8.jpg', 799.00, 749.00, 1, 1, 'Google'),
    ('Anker 30W Charger', 'anker-30w', 'USB-C fast charger', 'anker30w.jpg', 29.99, 24.99, 1, 2, 'Anker'),
    ('Spigen Case iPhone 15', 'spigen-15', 'Protective case', 'spigen15.jpg', 19.99, 17.99, 1, 2, 'Spigen');
