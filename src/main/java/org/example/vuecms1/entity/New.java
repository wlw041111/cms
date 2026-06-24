package org.example.vuecms1.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class New {
    private Integer id;
    private String newsTitle;
    private String newsType;
    private String valueHtml;
    private LocalDateTime createTime;
    private Integer staute;
}