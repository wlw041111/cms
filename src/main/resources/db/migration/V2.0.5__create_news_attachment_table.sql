CREATE TABLE IF NOT EXISTS cms.news_attachment
(
    id          BIGINT AUTO_INCREMENT                       COMMENT '附件ID'
        PRIMARY KEY,
    news_id     BIGINT                                      NOT NULL COMMENT '新闻ID',
    name        VARCHAR(255)                                NOT NULL COMMENT '附件原始文件名',
    url         VARCHAR(500)                                NOT NULL COMMENT '附件访问地址',
    file_size   BIGINT                                      NULL COMMENT '文件大小，单位字节',
    file_type   VARCHAR(100)                                NULL COMMENT '文件MIME类型',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP          NOT NULL COMMENT '创建时间',
    CONSTRAINT fk_news_attachment_news
        FOREIGN KEY (news_id) REFERENCES cms.news (id)
            ON UPDATE CASCADE ON DELETE CASCADE
) COMMENT '新闻附件表';

CREATE INDEX idx_news_attachment_news_id
    ON cms.news_attachment (news_id);
