package org.example.vuecms1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.vuecms1.entity.User;
import util.PageRequest;

import java.util.List;

public interface UserService {

    List<User> getUser();

    IPage<User> getUserPage(PageRequest pageRequest);

}
