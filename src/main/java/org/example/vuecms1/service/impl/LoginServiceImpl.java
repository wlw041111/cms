package org.example.vuecms1.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.dto.LoginDTO;
import org.example.vuecms1.entity.MenuType;
import org.example.vuecms1.entity.Permission;
import org.example.vuecms1.mapper.LoginMapper;
import org.example.vuecms1.service.LoginService;
import org.example.vuecms1.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;


    @Override
    public UserLoginVo login(LoginDTO loginDTO) {
        // 1. 基础登录验证
        UserLoginVo loginVO = loginMapper.login(loginDTO);
        log.info("loginVo的信息：{}", loginVO);

        if (loginVO == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 2. 构建菜单树
        if (loginVO.getPermissionList() != null) {
            List<Permission> menuTree = buildMenuTree(loginVO.getPermissionList());
            loginVO.setMenuTree(menuTree);
        }

        log.info("登录成功：{}", loginVO);
        return loginVO;
    }

    /**
     * 构建菜单树（过滤按钮，进行排序）
     */
    private List<Permission> buildMenuTree(List<Permission> permissions) {
        // 1. 过滤掉"按钮"类型的权限
        List<Permission> menus = permissions.stream()
                .filter(p -> p.getMenuType() != null && !p.getMenuType().equals(MenuType.BUTTON))
                .collect(Collectors.toList());

        List<Permission> tree = new ArrayList<>();
        for (Permission menu : menus) {
            // 2. 找到顶级节点
            if (menu.getParentId() == 0) {
                tree.add(findChildren(menu, menus));
            }
        }
        // 3. 顶级节点排序
        tree.sort(Comparator.comparing(Permission::getSort, Comparator.nullsLast(Integer::compareTo)));
        return tree;
    }

    /**
     * 递归寻找子节点
     */
    private Permission findChildren(Permission parent, List<Permission> allMenus) {
        parent.setChildren(new ArrayList<>());
        for (Permission it : allMenus) {
            if (parent.getId().equals(it.getParentId())) {
                parent.getChildren().add(findChildren(it, allMenus));
            }
        }
        // 子节点排序
        parent.getChildren().sort(Comparator.comparing(Permission::getSort, Comparator.nullsLast(Integer::compareTo)));
        return parent;
    }
}