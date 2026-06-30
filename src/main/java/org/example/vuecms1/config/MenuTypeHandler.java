package org.example.vuecms1.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.example.vuecms1.entity.MenuType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MenuType 自定义 TypeHandler — 处理大小写不敏感匹配
 * 数据库存储: DIRECTORY / MENU / button (混合大小写)
 * Java 枚举: DIRECTORY / MENU / BUTTON
 */
@MappedTypes(MenuType.class)
public class MenuTypeHandler extends BaseTypeHandler<MenuType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MenuType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public MenuType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return MenuType.fromString(rs.getString(columnName));
    }

    @Override
    public MenuType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return MenuType.fromString(rs.getString(columnIndex));
    }

    @Override
    public MenuType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return MenuType.fromString(cs.getString(columnIndex));
    }
}
