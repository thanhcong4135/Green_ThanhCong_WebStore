-- Seed roles
INSERT INTO roles (role_id, role_name) VALUES (1, 'ADMIN') ON DUPLICATE KEY UPDATE role_name=VALUES(role_name);
INSERT INTO roles (role_id, role_name) VALUES (2, 'USER') ON DUPLICATE KEY UPDATE role_name=VALUES(role_name);

-- Seed admin user (password: change-me, bcrypt hash placeholder)
INSERT INTO users (id, username, full_name, phone_number, password, enabled)
VALUES (1, 'admin', 'Administrator', '0000000000', '$2a$12$.V9meC8BEQndiK8oxTzgQ.UPqmoQoz2mHjl7D3/5P2LfDXKyN5b1y', 1)
ON DUPLICATE KEY UPDATE username=VALUES(username);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE role_id=VALUES(role_id);
