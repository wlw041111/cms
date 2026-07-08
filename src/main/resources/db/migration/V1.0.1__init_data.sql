-- 顶级目录：工作台
INSERT INTO cms.permission (name, type, code, path, parent_id, sort)
VALUES ('工作台', 'DIRECTORY', 'dashboard:view', '/dashboard', 0, 1);

-- 顶级目录：账号管理
INSERT INTO cms.permission (name, type, code, path, parent_id, sort)
VALUES ('账号管理', 'DIRECTORY', 'account:manage', '/account', 0, 2);

-- 菜单：用户信息（隶属于账号管理）
INSERT INTO cms.permission (name, type, code, path, parent_id, sort)
VALUES ('用户信息', 'MENU', 'account:user:list', '/account/user',
        (SELECT id FROM (SELECT id FROM cms.permission WHERE name = '账号管理' AND type = 'DIRECTORY') AS tmp), 1);

-- 顶级目录：身份权限
INSERT INTO cms.permission (name, type, code, path, parent_id, sort)
VALUES ('身份权限', 'DIRECTORY', 'role:manage', '/role', 0, 3);

-- 菜单：权限分配（隶属于身份权限）
INSERT INTO cms.permission (name, type, code, path, parent_id, sort)
VALUES ('权限分配', 'MENU', 'role:list', '/role/list',
        (SELECT id FROM (SELECT id FROM cms.permission WHERE name = '身份权限' AND type = 'DIRECTORY') AS tmp), 1);

-- 顶级目录：权限管理
INSERT INTO cms.permission (name, type, code, path, parent_id, sort)
VALUES ('权限管理', 'DIRECTORY', 'permission:manage', '/permission', 0, 4);

-- 菜单：权限信息（隶属于权限管理）
INSERT INTO cms.permission (name, type, code, path, parent_id, sort)
VALUES ('权限信息', 'MENU', 'permission:list', '/permission/list',
        (SELECT id FROM (SELECT id FROM cms.permission WHERE name = '权限管理' AND type = 'DIRECTORY') AS tmp), 1);