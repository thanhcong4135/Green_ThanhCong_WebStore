CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    code VARCHAR(100),
    description TEXT,
    photo VARCHAR(255),
    enabled TINYINT(1),
    parent_id INT,
    CONSTRAINT fk_categories_parent FOREIGN KEY (parent_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    code VARCHAR(100),
    description TEXT,
    photo VARCHAR(255),
    price DECIMAL(15,2),
    sale_price DECIMAL(15,2),
    enabled TINYINT(1),
    category_id INT,
    CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(id)
);
