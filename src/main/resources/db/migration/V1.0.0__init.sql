CREATE TABLE cms.permission
(
    id          INT AUTO_INCREMENT COMMENT '权限ID'
        PRIMARY KEY,
    name        VARCHAR(50)                                   NOT NULL COMMENT '权限名称，如：用户管理、新增用户',
    type        ENUM ('DIRECTORY', 'MENU', 'BUTTON')          NOT NULL COMMENT '权限类型：directory-目录, menu-菜单, button-按钮',
    code        VARCHAR(100)                                  NULL COMMENT '权限标识符，如：sys:user:add',
    path        VARCHAR(255)                                  NULL COMMENT '路由路径或接口路径',
    parent_id   INT      DEFAULT 0                            NULL COMMENT '父权限ID，0表示顶级',
    sort        INT      DEFAULT 0                            NULL COMMENT '排序值，数值越小越靠前',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP            NOT NULL COMMENT '创建时间',
    CONSTRAINT uk_permission_code
        UNIQUE (code)
) COMMENT '权限表（包含菜单和按钮）';

CREATE TABLE cms.role
(
    id   INT AUTO_INCREMENT COMMENT '角色ID'
        PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '角色名称，如：管理员、职员、普通用户',
    CONSTRAINT uk_role_name
        UNIQUE (name)
) COMMENT '角色表';

CREATE TABLE cms.role_permission
(
    role_id       INT NOT NULL COMMENT '角色ID',
    permission_id INT NOT NULL COMMENT '权限ID',
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_rp_permission
        FOREIGN KEY (permission_id) REFERENCES cms.permission (id)
            ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_rp_role
        FOREIGN KEY (role_id) REFERENCES cms.role (id)
            ON UPDATE CASCADE ON DELETE CASCADE
) COMMENT '角色权限关联表（多对多）';

CREATE TABLE cms.user
(
    id          BIGINT AUTO_INCREMENT                       COMMENT '用户ID'
        PRIMARY KEY,
    username    VARCHAR(100)                                NOT NULL COMMENT '用户名',
    password    VARCHAR(100) DEFAULT '123456'               NOT NULL COMMENT '密码（实际应加密）',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP      NOT NULL COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP      NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
    CONSTRAINT username
        UNIQUE (username)
) COMMENT '用户表';

CREATE TABLE cms.user_role
(
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id INT    NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT user_role_ibfk_1
        FOREIGN KEY (user_id) REFERENCES cms.user (id)
            ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT user_role_ibfk_2
        FOREIGN KEY (role_id) REFERENCES cms.role (id)
            ON UPDATE CASCADE ON DELETE CASCADE
) COMMENT '用户角色关联表（多对多）';

CREATE INDEX role_id
    ON cms.user_role (role_id);
