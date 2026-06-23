package org.example.vuecms1.service.impl;

import org.example.vuecms1.entity.User;
import org.example.vuecms1.mapper.UserMapper;
import org.example.vuecms1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

@Autowired
private UserMapper userMapper;

@Override
    public List<User> getUser() {
        return userMapper.getUser();
    }
}
