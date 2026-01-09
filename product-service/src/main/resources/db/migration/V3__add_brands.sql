CREATE TABLE IF NOT EXISTS brands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    code VARCHAR(100)
);

SET @col_exists := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE() AND table_name = 'products' AND column_name = 'brand_id'
);
SET @sql := IF(@col_exists = 0, 'ALTER TABLE products ADD COLUMN brand_id INT', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @constraint_exists := (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME = 'products'
      AND CONSTRAINT_NAME = 'fk_products_brand'
);
SET @sql2 := IF(@constraint_exists = 0,
    'ALTER TABLE products ADD CONSTRAINT fk_products_brand FOREIGN KEY (brand_id) REFERENCES brands(id)',
    'SELECT 1');
PREPARE stmt2 FROM @sql2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;

-- seed brands
INSERT INTO brands (id, name, code) VALUES
    (1, 'Apple', 'apple'),
    (2, 'Samsung', 'samsung'),
    (3, 'Google', 'google'),
    (4, 'Anker', 'anker'),
    (5, 'Spigen', 'spigen')
ON DUPLICATE KEY UPDATE name = VALUES(name), code = VALUES(code);
