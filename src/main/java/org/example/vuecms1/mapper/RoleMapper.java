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
}