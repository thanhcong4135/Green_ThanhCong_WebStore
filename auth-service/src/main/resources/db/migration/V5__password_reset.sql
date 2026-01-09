CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires_at DATETIME
);
