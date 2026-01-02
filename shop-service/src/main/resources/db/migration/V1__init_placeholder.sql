CREATE TABLE IF NOT EXISTS roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(255),
    phone_number VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    enabled TINYINT(1) DEFAULT 1,
    avatar VARCHAR(255),
    address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_users_roles_role FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

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

CREATE TABLE IF NOT EXISTS customers (
    customerId INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME VARCHAR(100) NOT NULL,
    LAST_NAME VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(64) NOT NULL UNIQUE,
    PASSWORD VARCHAR(128) NOT NULL,
    phone_number VARCHAR(12) UNIQUE,
    verified TINYINT(1) DEFAULT 0,
    verification_code VARCHAR(128) UNIQUE
);

CREATE TABLE IF NOT EXISTS address (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    STREET VARCHAR(128) NOT NULL,
    CITY VARCHAR(128) NOT NULL,
    customerId INT,
    CONSTRAINT fk_address_customer FOREIGN KEY (customerId) REFERENCES customers(customerId)
);

CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ADDRESS VARCHAR(255),
    TOTAL_PRICE DECIMAL(15,2),
    orderCode VARCHAR(100),
    STATUS VARCHAR(50),
    customer_customerId INT,
    CONSTRAINT fk_orders_customer FOREIGN KEY (customer_customerId) REFERENCES customers(customerId)
);

CREATE TABLE IF NOT EXISTS orderdetail (
    id INT AUTO_INCREMENT PRIMARY KEY,
    PRODUCT_NAME VARCHAR(255),
    PRICE DECIMAL(15,2),
    quantity INT,
    order_id INT,
    CONSTRAINT fk_orderdetail_order FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE IF NOT EXISTS persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) PRIMARY KEY,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL
);
