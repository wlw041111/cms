package org.example.vuecms1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.vuecms1.entity.Permission;
import org.example.vuecms1.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoleInfoPage();

    List<Permission> selectPermissionList();

    List<Integer> selectPermissionIdsByRoleId(Integer roleId);

    void deleteRolePermissions(Integer roleId);

    void insertRolePermissions(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> permissionIds);

    /**
     * 检查用户是否有某个权限（通过权限编码）
     */
    boolean hasPermissionByCode(@Param("userId") Integer userId, @Param("permissionCode") String permissionCode);

    /**
     * 获取用户所有权限编码
     */
    List<String> getUserPermissionCodes(@Param("userId") Integer userId);
}