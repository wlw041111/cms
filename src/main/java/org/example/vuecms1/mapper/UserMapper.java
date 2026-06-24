package org.example.vuecms1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.vuecms1.entity.New;
import org.example.vuecms1.entity.Role;
import org.example.vuecms1.entity.User;

import java.util.List;
@Mapper
public interface UserMapper {
 List<User> getUser();
int updateUser(User user);
 int adduser(User user);
 int deleteuser(User user);
 int insertUserRole(User user);
 /**
  * 插入新闻
  */
 int insert(New news);
 List<String> selectRole();
 /**
  * 查询所有新闻
  */
 List<New> selectAll();

 /**
  * 根据类型查询新闻
  */
 List<New> selectByType(@Param("newsType") String newsType);

 /**
  * 根据ID查询新闻详情
  */
 New selectById(@Param("id") Integer id);

 /**
  * 根据ID删除新闻
  */
// int deleteById(@Param("id") Integer id);

 /**
  * 更新新闻
  */
// int updateById(New news);
}
