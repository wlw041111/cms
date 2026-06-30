package org.example.vuecms1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.dto.RoleAuthorizeDTO;
import org.example.vuecms1.dto.RoleDTO;
import org.example.vuecms1.entity.Permission;
import org.example.vuecms1.entity.Role;
import org.example.vuecms1.mapper.RoleMapper;
import org.example.vuecms1.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    // 权限验证方法
    private boolean checkPermission(Integer userId, String permissionCode) {
        if (userId == null || permissionCode == null) {
            return false;
        }
        return roleMapper.hasPermissionByCode(userId, permissionCode);
    }

    private Map<String, Object> errorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }

    private Map<String, Object> successResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    /**
     * 获取当前用户的所有权限编码
     */
    @GetMapping("/user-permissions")
    public Object getUserPermissions(@RequestParam(required = true) Integer userId) {
        log.info("========== 获取用户角色权限 ==========");
        log.info("用户ID：" + userId);

        List<String> permissions = roleMapper.getUserPermissionCodes(userId);
        log.info("用户权限编码：" + permissions);

        return successResponse("获取成功", permissions);
    }

    /**
     * 获取角色列表 - 不需要权限验证，所有用户都可以查看
     */
    @PostMapping("/getRoleInfoPage")
    public Object getRoleInfoPage(@RequestParam(required = true) Integer userId,
                                  @RequestBody PageRequest pageRequest) {
        log.info("========== 收到获取角色列表请求 ==========");
        log.info("用户ID：" + userId);

        IPage<Role> page = roleService.getRoleInfoPage(pageRequest);
        return successResponse("获取成功", page);
    }

    /**
     * 新增角色 - 需要 role:add 权限
     */
    @PostMapping
    public Object createRole(@RequestParam(required = true) Integer userId,
                             @RequestBody RoleDTO roleDTO) {
        log.info("========== 收到新增角色请求 ==========");
        log.info("用户ID：" + userId);
        log.info("角色数据：" + roleDTO);

        if (!checkPermission(userId, "role:add")) {
            log.info("用户 " + userId + " 没有新增角色的权限");
            return errorResponse("无权操作");
        }

        Role role = roleService.createRole(roleDTO);
        return successResponse("角色已新增", role);
    }

    /**
     * 更新角色 - 需要 role:edit 权限
     */
    @PutMapping("/{id}")
    public Object updateRole(@PathVariable Integer id,
                             @RequestParam(required = true) Integer userId,
                             @RequestBody RoleDTO roleDTO) {
        log.info("========== 收到更新角色请求 ==========");
        log.info("用户ID：" + userId);
        log.info("角色ID：" + id);
        log.info("角色数据：" + roleDTO);

        if (!checkPermission(userId, "role:edit")) {
            log.info("用户 " + userId + " 没有编辑角色的权限");
            return errorResponse("无权操作");
        }

        Role role = roleService.updateRole(id, roleDTO);
        return successResponse("角色已更新", role);
    }

    /**
     * 删除角色 - 需要 role:delete 权限
     */
    @DeleteMapping("/{id}")
    public Object deleteRole(@PathVariable Integer id,
                             @RequestParam(required = true) Integer userId) {
        log.info("========== 收到删除角色请求 ==========");
        log.info("用户ID：" + userId);
        log.info("角色ID：" + id);

        if (!checkPermission(userId, "role:delete")) {
            log.info("用户 " + userId + " 没有删除角色的权限");
            return errorResponse("无权操作");
        }

        roleService.deleteRole(id);
        return successResponse("角色已删除", null);
    }

    /**
     * 获取权限树 - 需要 role:assign-permission 权限
     */
    @GetMapping("/permissionTree")
    public Object getPermissionTree(@RequestParam(required = true) Integer userId) {
        log.info("========== 收到获取权限树请求 ==========");
        log.info("用户ID：" + userId);

        if (!checkPermission(userId, "role:assign-permission")) {
            log.info("用户 " + userId + " 没有分配权限的权限");
            return errorResponse("无权操作");
        }

        List<Permission> tree = roleService.getPermissionTree();
        return successResponse("获取成功", tree);
    }

    /**
     * 获取角色已有权限ID - 需要 role:assign-permission 权限
     */
    @GetMapping("/{roleId}")
    public Object getRolePermissionIds(@PathVariable Integer roleId,
                                       @RequestParam(required = true) Integer userId) {
        log.info("========== 收到获取角色权限请求 ==========");
        log.info("用户ID：" + userId);
        log.info("角色ID：" + roleId);

        if (!checkPermission(userId, "role:assign-permission")) {
            log.info("用户 " + userId + " 没有分配权限的权限");
            return errorResponse("无权操作");
        }

        List<Integer> permissionIds = roleService.getRolePermissionIds(roleId);
        return successResponse("获取成功", permissionIds);
    }

    /**
     * 保存角色授权 - 需要 role:assign-permission 权限
     */
    @PostMapping("/authorize")
    public Object authorizeRole(@RequestParam(required = true) Integer userId,
                                @RequestBody RoleAuthorizeDTO roleAuthorizeDTO) {
        log.info("========== 收到角色授权请求 ==========");
        log.info("用户ID：" + userId);
        log.info("授权数据：" + roleAuthorizeDTO);

        if (!checkPermission(userId, "role:assign-permission")) {
            log.info("用户 " + userId + " 没有分配权限的权限");
            return errorResponse("无权操作");
        }

        roleService.authorizeRole(roleAuthorizeDTO);
        return successResponse("授权保存成功", null);
    }
}