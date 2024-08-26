INSERT INTO tb_role (co_role, no_role) VALUES (1, 'ADMIN') ON CONFLICT (co_role) DO NOTHING;
INSERT INTO tb_role (co_role, no_role) VALUES (2, 'MEMBER') ON CONFLICT (co_role) DO NOTHING;
