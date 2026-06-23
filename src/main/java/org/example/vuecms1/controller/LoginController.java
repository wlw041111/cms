package org.example.vuecms1.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.dto.LoginDTO;
import org.example.vuecms1.service.LoginService;
import org.example.vuecms1.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Result;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class LoginController {
    @Autowired
   private LoginService loginService;
    @PostMapping("/login")
    public Result<Object> login(@RequestBody LoginDTO loginDTO) {
        log.info("用户名:{},密码:{}",loginDTO.getUsername(),loginDTO.getPassword());

        UserLoginVo loginVo =loginService.login(loginDTO);
        if(loginVo != null) {
            return Result.success("登陆成功",loginVo);
        }
        return Result.fail(401,"登录失败");

    }
}
