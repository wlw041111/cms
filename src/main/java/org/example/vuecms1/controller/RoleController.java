package org.example.vuecms1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.vuecms1.dto.RoleAuthorizeDTO;
import org.example.vuecms1.dto.RoleDTO;
import org.example.vuecms1.entity.Permission;
import org.example.vuecms1.entity.Role;
import org.example.vuecms1.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.PageRequest;
import util.Result;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/getRoleInfoPage")
    public Result<IPage<Role>> getRoleInfoPage(@RequestBody PageRequest pageRequest) {
        return Result.success(roleService.getRoleInfoPage(pageRequest));
    }

    @PostMapping
    public Result<Role> createRole(@RequestBody RoleDTO roleDTO) {
        return Result.success("角色已新增", roleService.createRole(roleDTO));
    }

    @PutMapping("/{id}")
    public Result<Role> updateRole(@PathVariable Integer id, @RequestBody RoleDTO roleDTO) {
        return Result.success("角色已更新", roleService.updateRole(id, roleDTO));
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return Result.success("角色已删除");
    }

    @GetMapping("/permissionTree")
    public Result<List<Permission>> getPermissionTree() {
        return Result.success(roleService.getPermissionTree());
    }

    @GetMapping("/{roleId}")
    public Result<List<Integer>> getRolePermissionIds(@PathVariable Integer roleId) {
        return Result.success(roleService.getRolePermissionIds(roleId));
    }

    @PostMapping("/authorize")
    public Result<String> authorizeRole(@RequestBody RoleAuthorizeDTO roleAuthorizeDTO) {
        roleService.authorizeRole(roleAuthorizeDTO);
        return Result.success("授权保存成功");
    }
}