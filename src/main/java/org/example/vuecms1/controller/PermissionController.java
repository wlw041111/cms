package org.example.vuecms1.controller;

import org.example.vuecms1.dto.PermissionDTO;
import org.example.vuecms1.entity.Permission;
import org.example.vuecms1.mapper.PermissionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionMapper permissionMapper;

    @GetMapping("/list")
    public List<Permission> getPermissionList() {
        System.out.println("========== 收到获取权限列表请求 ==========");
        List<Permission> list = permissionMapper.selectList(null);
        System.out.println("查询到权限数量：" + (list != null ? list.size() : 0));
        return list;
    }

    @PostMapping("/add")
    public void createPermission(@RequestBody PermissionDTO permissionDTO) {
        System.out.println("========== 收到新增权限请求 ==========");
        System.out.println("权限数据：" + permissionDTO);
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDTO, permission);
        permission.setCreateTime(LocalDateTime.now());
        permissionMapper.insert(permission);
        System.out.println("新增成功");
    }

    @PutMapping("/update/{id}")
    public void updatePermission(@PathVariable Integer id, @RequestBody PermissionDTO permissionDTO) {
        System.out.println("========== 收到更新权限请求 ==========");
        System.out.println("权限ID：" + id);
        System.out.println("权限数据：" + permissionDTO);
        Permission permission = permissionMapper.selectById(id);
        if (permission != null) {
            BeanUtils.copyProperties(permissionDTO, permission);
            permissionMapper.updateById(permission);
            System.out.println("更新成功");
        } else {
            System.out.println("权限不存在");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deletePermission(@PathVariable Integer id) {
        System.out.println("========== 收到删除权限请求 ==========");
        System.out.println("权限ID：" + id);
        permissionMapper.deleteById(id);
        System.out.println("删除成功");
    }
}