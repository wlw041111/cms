package org.example.vuecms1.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.dto.PermissionDTO;
import org.example.vuecms1.entity.New;
import org.example.vuecms1.entity.Permission;
import org.example.vuecms1.mapper.NewMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.PageRequest;
import util.Result;

import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewController {

    @Autowired
    private NewMapper newMapper;
    @PostMapping("/update-status")
    public void updateNewsStatus(@RequestBody Map<String, Object> data) {
        Integer id = (Integer) data.get("id");
        Integer staute = (Integer) data.get("staute");
log.info("update news status id:{}, status:{}", id, staute);
        // 直接调用更新
        newMapper.updateStatus(id, staute);
    }

    @PostMapping("/update")
    public void updateNews(@RequestBody New news) {
        log.info("更新新闻：{}", news);
        newMapper.updatenew(news);
    }
    @DeleteMapping("/delete")
    public void deleteNews(@RequestParam Long id) {
        log.info("删除新闻，id:{}", id);
        newMapper.deleteById(id);
    }
}