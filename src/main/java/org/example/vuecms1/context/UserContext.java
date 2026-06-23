package org.example.vuecms1.context;

import org.example.vuecms1.vo.UserLoginVo;

public class UserContext {
    private static final ThreadLocal<UserLoginVo> USER_HOLDER = new ThreadLocal<>();

    public static void set(UserLoginVo user) {
        USER_HOLDER.set(user);
    }

    public static UserLoginVo get() {
        return USER_HOLDER.get();
    }

    public static void clear() {
        USER_HOLDER.remove();
    }
}