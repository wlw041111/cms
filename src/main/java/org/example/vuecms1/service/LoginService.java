package org.example.vuecms1.service;

import org.example.vuecms1.dto.LoginDTO;
import org.example.vuecms1.vo.UserLoginVo;
public interface LoginService {
    UserLoginVo login(LoginDTO loginDTO);
}
