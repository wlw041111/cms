package org.example.vuecms1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.entity.User;
import org.example.vuecms1.mapper.UserMapper;
import org.example.vuecms1.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    // ==================== 权限验证 ====================

    private boolean checkPermission(Integer userId, String permissionCode) {
        if (userId == null || permissionCode == null) {
            return false;
        }
        return userMapper.hasPermissionByCode(userId, permissionCode);
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
    @PostMapping("/user-permissions")
    public Object getUserPermissions(@RequestParam(required = true) Integer userId) {
        log.info("========== 获取用户权限 ==========");
        log.info("用户ID：" + userId);

        List<String> permissions = userMapper.getUserPermissionCodes(userId);
        log.info("用户权限编码：" + permissions);

        return successResponse("获取成功", permissions);
    }

    // ==================== 用户管理接口 ====================

    /**
     * 获取用户列表（分页） - 支持按用户名搜索
     */
    @PostMapping("/page")
    public Object getUserPage(@RequestParam(required = true) Integer userId,
                              @RequestBody PageRequest pageRequest) {
        log.info("========== 分页获取用户列表 ==========");
        log.info("用户ID：{}，分页参数：{}", userId, pageRequest);

        IPage<User> page = userService.getUserPage(pageRequest);
        return successResponse("获取成功", page);
    }

    /**
     * 获取用户列表 - 不需要权限验证，所有用户都可以查看
     */
    @PostMapping("/Userdata")
    public Object getUserdata(@RequestParam(required = true) Integer userId) {
        log.info("========== 获取用户列表 ==========");
        log.info("用户ID：" + userId);

        List<User> users = userService.getUser();
        return successResponse("获取成功", users);
    }

    /**
     * 更新用户 - 需要 user:edit 权限
     */
    @PostMapping("/updateUser")
    public Object updateUser(@RequestParam(required = true) Integer userId,
                             @RequestBody User user) {
        log.info("========== 更新用户 ==========");
        log.info("操作人ID：" + userId);
        log.info("更新数据：{}", user);

        if (!checkPermission(userId, "user:edit")) {
            log.info("用户 " + userId + " 没有修改用户的权限");
            return errorResponse("无权操作");
        }

        userMapper.updateUser(user);
        return successResponse("用户更新成功", null);
    }

    /**
     * 新增用户 - 需要 user:add 权限，默认密码123456，同时分配角色
     */
    @PostMapping("/adduser")
    public Object adduser(@RequestParam(required = true) Integer userId,
                          @RequestBody User user) {
        log.info("========== 新增用户 ==========");
        log.info("操作人ID：{}，新增数据：{}", userId, user);

        if (!checkPermission(userId, "user:add")) {
            log.info("用户 {} 没有新增用户的权限", userId);
            return errorResponse("无权操作");
        }

        // 默认密码123456
        if (!StringUtils.hasText(user.getPassword())) {
            user.setPassword("123456");
        }
        if (user.getCreatedTime() == null) {
            user.setCreatedTime(LocalDateTime.now());
        }
        if (user.getUpdatedTime() == null) {
            user.setUpdatedTime(LocalDateTime.now());
        }

        // 先创建用户（获取自增ID）
        userMapper.adduser(user);
        log.info("插入后 ID: {}", user.getId());

        // 同时分配角色
        if (StringUtils.hasText(user.getRole())) {
            userMapper.insertUserRole(user);
            log.info("已分配角色: {}", user.getRole());
        }

        return successResponse("用户新增成功", user);
    }

    /**
     * 删除用户 - 需要 user:delete 权限
     */
    @PostMapping("/deleteUser")
    public Object deleteUser(@RequestParam(required = true) Integer userId,
                             @RequestBody User user) {
        log.info("========== 删除用户 ==========");
        log.info("操作人ID：" + userId);
        log.info("删除用户ID：" + user.getId());

        if (!checkPermission(userId, "user:delete")) {
            log.info("用户 " + userId + " 没有删除用户的权限");
            return errorResponse("无权操作");
        }

        userMapper.deleteuser(user);
        return successResponse("用户删除成功", null);
    }

    /**
     * 插入用户角色 - 需要 user:edit 权限
     */
    @PostMapping("/insertUserRole")
    public Object insertUserRole(@RequestParam(required = true) Integer userId,
                                 @RequestBody User user) {
        log.info("========== 插入用户角色 ==========");
        log.info("操作人ID：" + userId);
        log.info("数据：{}", user);

        if (!checkPermission(userId, "user:edit")) {
            log.info("用户 " + userId + " 没有修改用户的权限");
            return errorResponse("无权操作");
        }

        userMapper.insertUserRole(user);
        return successResponse("角色分配成功", null);
    }

    /**
     * 获取所有角色 - 不需要权限验证
     */
    @PostMapping("/getUserRole")
    public Object getUserRole(@RequestParam(required = true) Integer userId) {
        log.info("========== 获取角色列表 ==========");
        log.info("用户ID：" + userId);

        List<String> roles = userMapper.selectRole();
        return successResponse("获取成功", roles);
    }

}