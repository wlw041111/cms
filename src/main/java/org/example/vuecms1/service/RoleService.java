package org.example.vuecms1.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.vuecms1.dto.RoleAuthorizeDTO;
import org.example.vuecms1.dto.RoleDTO;
import org.example.vuecms1.entity.Permission;
import org.example.vuecms1.entity.Role;
import util.PageRequest;

import java.util.List;

public interface RoleService {
    IPage<Role> getRoleInfoPage(PageRequest pageRequest);

    Role createRole(RoleDTO roleDTO);

    Role updateRole(Integer id, RoleDTO roleDTO);

    void deleteRole(Integer id);

    List<Permission> getPermissionTree();

    List<Integer> getRolePermissionIds(Integer roleId);

    void authorizeRole(RoleAuthorizeDTO roleAuthorizeDTO);
}