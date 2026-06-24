package org.example.vuecms1.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.entity.New;
import org.example.vuecms1.entity.User;
import org.example.vuecms1.mapper.NewMapper;
import org.example.vuecms1.mapper.UserMapper;
import org.example.vuecms1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewMapper newMapper;

    @PostMapping("/Userdata")
    public List<User> getUserdata() {
        log.info("User数据{}", userService.getUser());
        return userService.getUser();
    }

    @PostMapping("/updateUser")
    public List<User> updateUser(@RequestBody User user) {
        log.info("User<UNK>更新{}", user);
        userMapper.updateUser(user);
        return null;
    }

    @PostMapping("/adduser")
    public User adduser(@RequestBody User user) {  // ✅ 改成 User
        log.info("新增用户: {}", user);
        userMapper.adduser(user);
        log.info("插入后 ID: {}", user.getId());
        return user;  // ✅ 返回包含 ID 的 user
    }

    @PostMapping("/deleteUser")
    public List<User> deleteUser(@RequestBody User user) {
        log.info("User<UNK>{}", user);
        userMapper.deleteuser(user);
        return null;
    }

    @PostMapping("/insertUserRole")
    public void insertUserRole(@RequestBody User user) {
        log.info("后端返回的数据：{}", user);
          userMapper.insertUserRole(user);


    }

    @PostMapping("/getUserRole")
    public List<String> getUserRole() {
       return userMapper.selectRole();


    }


    // ===== 新闻相关接口 =====

    @PostMapping("/savenews")
    public String saveNews(@RequestBody New news) {
        log.info("保存新闻{}", news);
        userMapper.insert(news);
        return "success";
        //
    }

    /**
     * 获取所有新闻列表 - 直接返回数据
     */
    @GetMapping("/newslist")
    public List<New> getNewsList() {

        List<New> newsList = userMapper.selectAll();
        log.info("获取新闻列表{}", newsList);
        return newsList;
    }

    /**
     * 根据类型获取新闻 - 直接返回数据
     */
    @GetMapping("/newsbytype")
    public List<New> getNewsByType(@RequestParam String newsType) {
        log.info("根据类型获取新闻：{}", newsType);
        List<New> newsList = userMapper.selectByType(newsType);
        return newsList;
    }

    /**
     * 根据ID获取新闻详情 - 直接返回数据
     */
    @GetMapping("/newsdetail")
    public New getNewsDetail(@RequestParam Integer id) {
        log.info("获取新闻详情：{}", userMapper.selectById(id));
        return userMapper.selectById(id);
    }
}