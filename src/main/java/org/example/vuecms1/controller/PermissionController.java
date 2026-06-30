package org.example.vuecms1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.dto.PermissionDTO;
import org.example.vuecms1.entity.Permission;
import org.example.vuecms1.mapper.PermissionMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import util.PageRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionMapper permissionMapper;

    // 权限验证方法 - 使用权限编码
    private boolean checkPermission(Integer userId, String permissionCode) {
        if (userId == null || permissionCode == null) {
            return false;
        }
        return permissionMapper.hasPermissionByCode(userId, permissionCode);
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
        log.info("========== 获取用户权限 ==========");
        log.info("用户ID：" + userId);

        List<String> permissions = permissionMapper.getUserPermissionCodes(userId);
        log.info("用户权限编码：" + permissions);

        return successResponse("获取成功", permissions);
    }

    /**
     * 获取权限列表（分页） - 支持按名称搜索
     */
    @PostMapping("/page")
    public Object getPermissionPage(@RequestParam(required = true) Integer userId,
                                    @RequestBody PageRequest pageRequest) {
        log.info("========== 收到分页获取权限列表请求 ==========");
        log.info("用户ID：" + userId);
        log.info("分页参数：" + pageRequest);

        Page<Permission> page = new Page<>(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();

        String name = pageRequest.getParamValue("name");
        if (StringUtils.hasText(name)) {
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByAsc("parent_id", "sort", "id");

        IPage<Permission> result = permissionMapper.selectPage(page, queryWrapper);
        log.info("查询到权限数量：" + result.getTotal());

        return successResponse("获取成功", result);
    }

    /**
     * 获取权限列表 - 不需要权限验证，所有登录用户都可以查看
     */
    @GetMapping("/list")
    public Object getPermissionList(@RequestParam(required = true) Integer userId) {
        log.info("========== 收到获取权限列表请求 ==========");
        log.info("用户ID：" + userId);

        List<Permission> list = permissionMapper.selectList(null);
        log.info("查询到权限数量：" + (list != null ? list.size() : 0));

        return successResponse("获取成功", list);
    }

    /**
     * 新增权限 - 需要 role:add 权限
     */
    @PostMapping("/add")
    public Object createPermission(@RequestParam(required = true) Integer userId,
                                   @RequestBody PermissionDTO permissionDTO) {
        log.info("========== 收到新增权限请求 ==========");
        log.info("用户ID：" + userId);
        log.info("权限数据：" + permissionDTO);

        if (!checkPermission(userId, "role:add")) {
            log.info("用户 " + userId + " 没有新增权限的权限");
            return errorResponse("无权操作");
        }

        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionDTO, permission);
        permission.setCreateTime(LocalDateTime.now());
        permissionMapper.insert(permission);
        log.info("新增成功");

        return successResponse("权限新增成功", null);
    }

    /**
     * 更新权限 - 需要 role:edit 权限
     */
    @PutMapping("/update/{id}")
    public Object updatePermission(@PathVariable Integer id,
                                   @RequestParam(required = true) Integer userId,
                                   @RequestBody PermissionDTO permissionDTO) {
        log.info("========== 收到更新权限请求 ==========");
        log.info("用户ID：" + userId);
        log.info("权限ID：" + id);
        log.info("权限数据：" + permissionDTO);

        if (!checkPermission(userId, "role:edit")) {
            log.info("用户 " + userId + " 没有编辑权限的权限");
            return errorResponse("无权操作");
        }

        Permission permission = permissionMapper.selectById(id);
        if (permission != null) {
            BeanUtils.copyProperties(permissionDTO, permission);
            permissionMapper.updateById(permission);
            log.info("更新成功");
            return successResponse("权限更新成功", null);
        } else {
            return errorResponse("权限不存在");
        }
    }

    /**
     * 删除权限 - 需要 role:delete 权限
     */
    @DeleteMapping("/delete/{id}")
    public Object deletePermission(@PathVariable Integer id,
                                   @RequestParam(required = true) Integer userId) {
        log.info("========== 收到删除权限请求 ==========");
        log.info("用户ID：" + userId);
        log.info("权限ID：" + id);

        if (!checkPermission(userId, "role:delete")) {
            log.info("用户 " + userId + " 没有删除权限的权限");
            return errorResponse("无权操作");
        }

        permissionMapper.deleteById(id);
        log.info("删除成功");

        return successResponse("权限删除成功", null);
    }
}