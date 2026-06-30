package org.example.vuecms1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.entity.New;
import org.example.vuecms1.mapper.UserMapper;
import org.example.vuecms1.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewController {

    @Autowired
    private NewService newService;

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
        log.info("========== 获取新闻管理权限 ==========");
        log.info("用户ID：" + userId);

        List<String> permissions = userMapper.getUserPermissionCodes(userId);
        log.info("用户权限编码：" + permissions);

        return successResponse("获取成功", permissions);
    }

    // ==================== 新闻管理接口 ====================

    /**
     * 获取新闻列表（全部数据）- 供首页和审核页使用
     */
    @GetMapping("/newslist")
    public Object getNewsList(@RequestParam(required = true) Integer userId) {
        log.info("========== 获取新闻列表 ==========");
        log.info("用户ID：" + userId);

        List<New> newsList = newService.getAllNews();
        return successResponse("获取成功", newsList);
    }

    /**
     * 分页获取新闻列表 - 供新闻管理列表页使用
     */
    @GetMapping("/newslist-page")
    public Object getNewsListPage(@RequestParam(required = true) Integer userId,
                                   @RequestParam(defaultValue = "1") int currentPage,
                                   @RequestParam(defaultValue = "10") int pageSize) {
        log.info("========== 分页获取新闻列表 ==========");
        log.info("用户ID：" + userId);
        log.info("页码：" + currentPage + "，每页大小：" + pageSize);

        IPage<New> pageResult = newService.getNewsPage(currentPage, pageSize);

        Map<String, Object> pageData = new HashMap<>();
        pageData.put("records", pageResult.getRecords());
        pageData.put("total", pageResult.getTotal());
        pageData.put("currentPage", pageResult.getCurrent());
        pageData.put("pageSize", pageResult.getSize());
        pageData.put("totalPages", pageResult.getPages());

        return successResponse("获取成功", pageData);
    }

    /**
     * 根据类型获取新闻 - 不需要权限验证
     */
    @GetMapping("/newsbytype")
    public Object getNewsByType(@RequestParam(required = true) Integer userId,
                                @RequestParam String newsType) {
        log.info("========== 根据类型获取新闻 ==========");
        log.info("用户ID：" + userId);
        log.info("栏目：" + newsType);

        List<New> newsList = newService.getNewsByType(newsType);
        return successResponse("获取成功", newsList);
    }

    /**
     * 获取新闻详情 - 不需要权限验证
     */
    @GetMapping("/newsdetail")
    public Object getNewsDetail(@RequestParam(required = true) Integer userId,
                                @RequestParam Long id) {
        log.info("========== 获取新闻详情 ==========");
        log.info("用户ID：" + userId);
        log.info("新闻ID：" + id);

        New news = newService.getNewsDetail(id);
        return successResponse("获取成功", news);
    }

    /**
     * 保存新闻 - 需要 content:news:audit 权限
     */
    @PostMapping("/savenews")
    public Object saveNews(@RequestParam(required = true) Integer userId,
                           @RequestBody New news) {
        log.info("========== 保存新闻 ==========");
        log.info("操作人ID：" + userId);
        log.info("新闻数据：{}", news);

        if (!checkPermission(userId, "content:news:audit")) {
            log.info("用户 " + userId + " 没有审核新闻的权限");
            return errorResponse("无权操作");
        }

        // 新建新闻默认状态为待审核
        news.setStatus("PENDING_REVIEW");
        newService.saveNews(news);
        return successResponse("新闻保存成功", null);
    }

    /**
     * 更新新闻状态（通过/驳回）- 需要 content:news:audit 权限
     */
    @PostMapping("/update-status")
    public Object updateNewsStatus(@RequestParam(required = true) Integer userId,
                                   @RequestBody Map<String, Object> data) {
        log.info("========== 更新新闻状态 ==========");
        log.info("操作人ID：" + userId);
        log.info("原始请求数据：" + data);
        log.info("id 类型：" + data.get("id").getClass().getName() + "，值：" + data.get("id"));
        log.info("status 类型：" + data.get("status").getClass().getName() + "，值：" + data.get("status"));

        // 兼容前端传 number 或 string 的 id
        Long id = Long.valueOf(String.valueOf(data.get("id")));
        String status = String.valueOf(data.get("status"));

        log.info("转换后 id=" + id + ", status=" + status);

        if (!checkPermission(userId, "content:news:audit")) {
            log.info("用户 " + userId + " 没有审核新闻的权限");
            return errorResponse("无权操作");
        }

        newService.updateNewsStatus(id, status);
        log.info("更新完成，id=" + id + ", status=" + status);
        return successResponse("状态更新成功", null);
    }

    /**
     * 更新新闻 - 需要 content:news:audit 权限
     */
    @PostMapping("/update")
    public Object updateNews(@RequestParam(required = true) Integer userId,
                             @RequestBody New news) {
        log.info("========== 更新新闻 ==========");
        log.info("操作人ID：" + userId);
        log.info("更新数据：{}", news);

        if (!checkPermission(userId, "content:news:audit")) {
            log.info("用户 " + userId + " 没有审核新闻的权限");
            return errorResponse("无权操作");
        }

        newService.updateNews(news);
        return successResponse("新闻更新成功", null);
    }

    /**
     * 删除新闻 - 需要 content:news:audit 权限
     */
    @DeleteMapping("/delete")
    public Object deleteNews(@RequestParam(required = true) Integer userId,
                             @RequestParam Long id) {
        log.info("========== 删除新闻 ==========");
        log.info("操作人ID：" + userId);
        log.info("新闻ID：" + id);

        if (!checkPermission(userId, "content:news:audit")) {
            log.info("用户 " + userId + " 没有审核新闻的权限");
            return errorResponse("无权操作");
        }

        newService.deleteNews(id);
        return successResponse("新闻删除成功", null);
    }
}
