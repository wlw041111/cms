CREATE TABLE IF NOT EXISTS cms.news
(
    id           BIGINT AUTO_INCREMENT                      COMMENT '新闻ID'
        PRIMARY KEY,
    title        VARCHAR(120)                               NOT NULL COMMENT '新闻标题',
    category     VARCHAR(50)                                NOT NULL COMMENT '栏目',
    supplier     VARCHAR(50)                                NULL COMMENT '供稿人',
    reviewer     VARCHAR(50)                                NULL COMMENT '审稿人',
    content      LONGTEXT                                   NOT NULL COMMENT '新闻正文HTML',
    status       VARCHAR(30) DEFAULT 'PENDING_REVIEW'       NOT NULL COMMENT '状态',
    create_time  DATETIME    DEFAULT CURRENT_TIMESTAMP      NOT NULL COMMENT '创建时间',
    update_time  DATETIME    DEFAULT CURRENT_TIMESTAMP      NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    publish_time DATETIME                                   NULL COMMENT '发布时间'
) COMMENT '新闻表';
