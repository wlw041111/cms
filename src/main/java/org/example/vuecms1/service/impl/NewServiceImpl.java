package org.example.vuecms1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.vuecms1.entity.New;
import org.example.vuecms1.mapper.NewMapper;
import org.example.vuecms1.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewServiceImpl implements NewService {

    @Autowired
    private NewMapper newMapper;

    @Override
    public List<New> getAllNews() {
        return newMapper.selectAll();
    }

    @Override
    public IPage<New> getNewsPage(int currentPage, int pageSize) {
        Page<New> page = new Page<>(currentPage, pageSize);
        return newMapper.selectPage(page);
    }

    @Override
    public List<New> getNewsByType(String category) {
        return newMapper.selectByType(category);
    }

    @Override
    public New getNewsDetail(Long id) {
        return newMapper.selectById(id);
    }

    @Override
    public New saveNews(New news) {
        newMapper.insert(news);
        return news;
    }

    @Override
    public New updateNews(New news) {
        newMapper.updatenew(news);
        return news;
    }

    @Override
    public void updateNewsStatus(Long id, String status) {
        newMapper.updateStatus(id, status);
    }

    @Override
    public void deleteNews(Long id) {
        newMapper.deleteById(id);
    }
}
