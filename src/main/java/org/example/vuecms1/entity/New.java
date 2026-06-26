package org.example.vuecms1.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
@TableName("new_save")
@Data
public class New {
    private Integer id;
    private String newsTitle;
    private String newsType;
    private String valueHtml;
    private LocalDateTime createTime;
    private Integer staute;
}