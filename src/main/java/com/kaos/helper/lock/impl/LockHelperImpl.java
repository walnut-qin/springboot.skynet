package com.kaos.helper.lock.impl;

import java.util.ArrayList;
import java.util.List;

import com.kaos.helper.lock.LockHelper;

import org.apache.log4j.Logger;

public class LockHelperImpl implements LockHelper {
    /**
     * 日志工具
     */
    Logger logger = Logger.getLogger(LockHelperImpl.class.getName());

    /**
     * 锁名称
     */
    String name = null;

    /**
     * 锁实体
     */
    List<Object> locks = null;

    /**
     * 构造函数
     * 
     * @param cnt 锁数量
     */
    public LockHelperImpl(String name, Integer cnt) {
        // 初始化锁名称
        this.name = name;

        // 初始化锁对象
        this.locks = new ArrayList<>();
        for (int i = 0; i < cnt; i++) {
            this.locks.add(new Object());
        }

        // 记录日志
        this.logger.debug(String.format("初始化锁(name = %s, cnt = %d)", name, cnt));
    }

    @Override
    public Object mapToLock(String src) {
        // 初始日志
        this.logger.debug(String.format("获取锁对象(src = %s)", src));

        // 计算索引
        var dst = src.replaceAll("[^0-9]", "");
        this.logger.debug(String.format("剔除非数字字符(%s -> %s)", src, dst));

        // 映射到有效索引
        var idx = Long.valueOf(dst) % this.locks.size();
        this.logger.debug(String.format("映射到有效索引(%s -> %d)", dst, idx));

        var lock = this.locks.get((int) idx);
        this.logger.debug(String.format("检索锁对象(hash = %d)", lock.hashCode()));

        return lock;
    }
}
