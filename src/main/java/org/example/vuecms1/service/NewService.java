package org.example.vuecms1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.vuecms1.entity.New;

import java.util.List;

public interface NewService {
    List<New> getAllNews();
    IPage<New> getNewsPage(int currentPage, int pageSize);
    List<New> getNewsByType(String category);
    New getNewsDetail(Long id);
    New saveNews(New news);
    New updateNews(New news);
    void updateNewsStatus(Long id, String status);
    void deleteNews(Long id);
}
