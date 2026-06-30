INSERT IGNORE INTO cms.role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM cms.role r
         JOIN cms.permission p
WHERE r.name IN ('超级管理员', '管理员')
  AND p.code IN ('content:manage', 'content:news:list');
