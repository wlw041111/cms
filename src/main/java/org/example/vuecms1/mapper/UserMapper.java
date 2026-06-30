package org.example.vuecms1.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.vuecms1.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getUser();

    IPage<User> getUserPage(Page<User> page, @Param("username") String username);

    int updateUser(User user);

    int adduser(User user);

    int deleteuser(User user);

    int insertUserRole(User user);

    List<String> selectRole();

    boolean hasPermissionByCode(@Param("userId") Integer userId, @Param("permissionCode") String permissionCode);

    List<String> getUserPermissionCodes(@Param("userId") Integer userId);
}