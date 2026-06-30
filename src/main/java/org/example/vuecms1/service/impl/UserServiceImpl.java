package org.example.vuecms1.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.vuecms1.entity.User;
import org.example.vuecms1.mapper.UserMapper;
import org.example.vuecms1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.PageRequest;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUser() {
        return userMapper.getUser();
    }

    @Override
    public IPage<User> getUserPage(PageRequest pageRequest) {
        Page<User> page = new Page<>(pageRequest.getCurrentPage(), pageRequest.getPageSize());
        String username = pageRequest.getParamValue("username");
        return userMapper.getUserPage(page, username);
    }
}
