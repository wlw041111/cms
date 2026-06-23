package org.example.vuecms1.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.vuecms1.dto.PermissionDTO;
import org.example.vuecms1.entity.Permission;
import util.PageRequest;

import java.util.List;

public interface PermissionService {
    IPage<Permission> getPermissionPage(PageRequest pageRequest);

    List<Permission> getPermissionTree();

    Permission createPermission(PermissionDTO permissionDTO);

    Permission updatePermission(Integer id, PermissionDTO permissionDTO);

    void deletePermission(Integer id);
}