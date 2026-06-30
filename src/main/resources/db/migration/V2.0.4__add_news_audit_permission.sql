INSERT IGNORE INTO cms.permission (name, type, code, path, parent_id, sort, create_time)
SELECT '审核新闻', 'BUTTON', 'content:news:audit', NULL, p.id, 0, NOW()
FROM cms.permission p
WHERE p.code = 'content:news:list';

INSERT IGNORE INTO cms.role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM cms.role r
         JOIN cms.permission p
WHERE r.name IN ('超级管理员', '管理员')
  AND p.code = 'content:news:audit';
