CREATE TABLE IF NOT EXISTS order_tracking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    status VARCHAR(64),
    description VARCHAR(255),
    location VARCHAR(255),
    event_time DATETIME,
    CONSTRAINT fk_tracking_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
