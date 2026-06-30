package org.example.vuecms1.entity;

public enum MenuType {
    DIRECTORY("目录"),
    MENU("菜单"),
    BUTTON("按钮");

    private String value;

    MenuType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 大小写不敏感地从字符串转换为 MenuType 枚举
     */
    public static MenuType fromString(String str) {
        if (str == null) {
            return null;
        }
        for (MenuType type : values()) {
            if (type.name().equalsIgnoreCase(str)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown MenuType: " + str);
    }
}