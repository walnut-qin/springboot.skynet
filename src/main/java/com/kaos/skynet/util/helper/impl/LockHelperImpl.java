package com.kaos.skynet.util.helper.impl;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.kaos.skynet.util.helper.LockHelper;

import org.apache.log4j.Logger;

/**
 * 分段锁
 */
public class LockHelperImpl implements LockHelper {
    /**
     * 日志工具
     */
    Logger logger = Logger.getLogger(LockHelperImpl.class);

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
     * @param size 锁数量
     */
    public LockHelperImpl(Integer size) {
        this.locks = Lists.newArrayListWithCapacity(size);
        while (this.locks.size() < size) {
            this.locks.add(new Object());
        }
    }

    /**
     * 构造函数
     * 
     * @param name 锁名
     * @param size 锁数量
     */
    public LockHelperImpl(String name, Integer size) {
        this(size);
        this.name = name;
    }

    @Override
    public Integer getSize() {
        return this.locks.size();
    }

    @Override
    public Object mapToLock(String src) {
        // 初始日志
        this.logger.debug(String.format("获取锁对象(name = %s, src = %s)", this.name, src));

        // 计算索引
        var dst = src.replaceAll("[^0-9]", "");
        this.logger.debug(
                String.format("提取数字(name = %s, %s -> %s)", Optional.fromNullable(this.name).or("匿名"), src, dst));

        // 映射到有效索引
        var idx = Long.valueOf(dst) % this.locks.size();
        this.logger.debug(
                String.format("映射索引(name = %s, %s -> %d)", Optional.fromNullable(this.name).or("匿名"), dst, idx));

        var lock = this.locks.get((int) idx);
        this.logger.debug(
                String.format("定位锁(name = %s, hash = %d)", Optional.fromNullable(this.name).or("匿名"), lock.hashCode()));

        return lock;
    }
}
