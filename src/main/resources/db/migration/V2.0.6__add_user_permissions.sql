-- ==================== 用户操作权限 ====================
-- 插入用户操作权限：新增用户、编辑用户、删除用户，挂到"用户信息"菜单下
INSERT IGNORE INTO cms.permission (name, type, code, path, parent_id, sort, create_time)
VALUES ('新增用户', 'BUTTON', 'user:add', NULL,
        (SELECT id FROM (SELECT id FROM cms.permission WHERE code = 'account:user:list') AS tmp), 0, NOW()),
       ('编辑用户', 'BUTTON', 'user:edit', NULL,
        (SELECT id FROM (SELECT id FROM cms.permission WHERE code = 'account:user:list') AS tmp), 0, NOW()),
       ('删除用户', 'BUTTON', 'user:delete', NULL,
        (SELECT id FROM (SELECT id FROM cms.permission WHERE code = 'account:user:list') AS tmp), 0, NOW());

-- 给超级管理员和管理员分配用户操作权限
INSERT IGNORE INTO cms.role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM cms.role r
         JOIN cms.permission p
WHERE r.name IN ('超级管理员', '管理员')
  AND p.code IN ('user:add', 'user:edit', 'user:delete');

-- ==================== 角色操作权限（V2.0.1 漏了分配） ====================
-- 给超级管理员和管理员分配角色操作权限
INSERT IGNORE INTO cms.role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM cms.role r
         JOIN cms.permission p
WHERE r.name IN ('超级管理员', '管理员')
  AND p.code IN ('role:add', 'role:edit', 'role:delete', 'role:assign-permission');
