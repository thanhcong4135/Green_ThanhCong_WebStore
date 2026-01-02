INSERT INTO roles (role_id, role_name) VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

INSERT INTO users (id, username, full_name, phone_number, password, enabled, avatar, address) VALUES
    (1, 'admin', 'Admin User', '0900000000', '$2a$12$pnv.znOvT6Z5ZRxqCR58y.WG06FtZc4mEUpySYx.rjig.gG2OwL6.', 1, NULL, '123 Admin St'),
    (2, 'john', 'John Doe', '0900000001', '$2a$12$pnv.znOvT6Z5ZRxqCR58y.WG06FtZc4mEUpySYx.rjig.gG2OwL6.', 1, NULL, '123 Main St'),
    (3, 'jane', 'Jane Smith', '0900000002', '$2a$12$pnv.znOvT6Z5ZRxqCR58y.WG06FtZc4mEUpySYx.rjig.gG2OwL6.', 1, NULL, '456 Market St');

INSERT INTO users_roles (user_id, role_id) VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2);

-- Password hash is bcrypt for string "123456789"
