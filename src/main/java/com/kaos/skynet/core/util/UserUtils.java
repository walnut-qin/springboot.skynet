package com.kaos.skynet.core.util;

import com.kaos.skynet.core.api.data.entity.KaosUser;

public final class UserUtils {
    /**
     * 定义线程变量
     */
    final static ThreadLocal<KaosUser> kaosUser = new ThreadLocal<>();

    /**
     * 禁用实例化
     */
    private UserUtils() {
    }

    /**
     * 创建用户实体
     */
    public static void create(KaosUser newUser) {
        kaosUser.set(newUser);
    }

    /**
     * 销毁用户实体
     */
    public static void destroy() {
        kaosUser.remove();
    }

    /**
     * 获取当前登入的用户
     * 
     * @return
     */
    public static KaosUser currentUser() {
        return kaosUser.get();
    }
}
