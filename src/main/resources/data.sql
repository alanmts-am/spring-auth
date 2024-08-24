INSERT INTO tb_role (role) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (role) VALUES ('ROLE_MEMBER');

INSERT INTO tb_user (username, password) VALUES ('admin', '$2a$12$k76EK/urs40uG6WbF41Gve0i5vb0ThF3FldcBHggd5S/SKY/RpY4q');

INSERT INTO tb_user_roles (user_id, roles_role) 
SELECT u.id, r.role
FROM tb_user u
JOIN tb_role r ON r.role = 'ROLE_ADMIN'
WHERE u.username = 'admin';
