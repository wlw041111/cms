-- 1. 先插入父级"内容管理"
INSERT INTO cms.permission (name, type, code, path, parent_id, sort, create_time)
VALUES ('内容管理', 'DIRECTORY', 'content:manage', '/content', 0, 5, '2026-05-06 19:46:12');

-- 2. 再插入子菜单，通过子查询动态获取 parent_id
INSERT INTO cms.permission (name, type, code, path, parent_id, sort, create_time)
VALUES ('新闻信息', 'MENU', 'content:news:list', '/content/news',
        (SELECT id FROM (SELECT id FROM cms.permission WHERE name = '内容管理' AND type = 'DIRECTORY') AS tmp), 1,
        '2026-05-06 19:46:12'),

       ('公告信息', 'MENU', 'content:notice:list', '/content/notice',
        (SELECT id FROM (SELECT id FROM cms.permission WHERE name = '内容管理' AND type = 'DIRECTORY') AS tmp), 2,
        '2026-05-06 19:46:12'),

       ('学术活动信息', 'MENU', 'content:activity:list', '/content/activity',
        (SELECT id FROM (SELECT id FROM cms.permission WHERE name = '内容管理' AND type = 'DIRECTORY') AS tmp), 3,
        '2026-05-06 19:46:12'),

       ('党建工作信息', 'MENU', 'content:party:list', '/content/party',
        (SELECT id FROM (SELECT id FROM cms.permission WHERE name = '内容管理' AND type = 'DIRECTORY') AS tmp), 4,
        '2026-05-06 19:46:12'),

       ('学工新闻信息', 'MENU', 'content:studentnews:list', '/content/studentnews',
        (SELECT id FROM (SELECT id FROM cms.permission WHERE name = '内容管理' AND type = 'DIRECTORY') AS tmp), 5,
        '2026-05-06 19:46:12');
