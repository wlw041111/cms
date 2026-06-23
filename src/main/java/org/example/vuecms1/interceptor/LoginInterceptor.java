package org.example.vuecms1.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.vuecms1.context.UserContext;
import org.example.vuecms1.vo.UserLoginVo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        log.info("----------------拦截器------------------");

        // 1. 获取 Header 中的 Token
        String token = request.getHeader("Authorization");

        // 2. 如果没有 token，放行或根据需求处理
        if (token == null || token.isEmpty()) {
            log.warn("未检测到 Token，请求路径: {}", request.getRequestURI());
            // 这里可以根据需求决定是否放行
            // 如果需要登录才能访问，抛出异常
            // throw new RuntimeException("请先登录");

            // 或者直接放行（不拦截）
            return true;
        }

        // 3. 如果有 token，可以做一些简单的校验
        log.info("Token: {}", token);

        // TODO: 这里可以添加你的认证逻辑
        // 例如：解析 JWT 或验证 Token 有效性

        // 4. 模拟获取用户信息（实际项目中从 Token 解析）
        // UserLoginVo loginUser = parseToken(token);
        // UserContext.set(loginUser);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // 请求结束后清理上下文
        UserContext.clear();
    }
}