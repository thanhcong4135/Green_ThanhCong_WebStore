-- Ensure brand_name column exists for legacy data mapping
SET @col_exists := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'products'
      AND column_name = 'brand_name'
);
SET @sql := IF(@col_exists = 0,
    'ALTER TABLE products ADD COLUMN brand_name VARCHAR(255)',
    'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
