package org.example.vuecms1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.vuecms1.dto.LoginDTO;
import org.example.vuecms1.vo.UserLoginVo;
// 删除 import org.example.vuecms1.vo.LoginVo;

@Mapper
public interface LoginMapper {
    UserLoginVo login(LoginDTO loginDTO);  // 返回类型改为 User
}