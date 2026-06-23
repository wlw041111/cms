package org.example.vuecms1.dto;

import org.example.vuecms1.entity.MenuType;
import lombok.Data;

@Data
public class PermissionDTO {
    private String name;
    private MenuType menuType;
    private String code;
    private String path;
    private Integer parentId;
    private Integer sort;
}