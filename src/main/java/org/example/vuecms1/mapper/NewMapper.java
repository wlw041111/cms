package org.example.vuecms1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.vuecms1.entity.New;

import java.util.List;

@Mapper
public interface NewMapper extends BaseMapper<New> {
    int updateStatus(@Param("id") Long id, @Param("status") String status);

    int updatenew(New news);

    List<New> selectAll();

    IPage<New> selectPage(Page<New> page);

    List<New> selectByType(@Param("category") String category);
}
