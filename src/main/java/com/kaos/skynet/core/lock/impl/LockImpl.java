package com.kaos.skynet.core.lock.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.lock.Lock;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.log4j.Log4j;

/**
 * 分段锁
 */
@Log4j
public class LockImpl<T> implements Lock<T> {
    /**
     * 锁名称
     */
    String name = null;

    /**
     * 锁实体
     */
    List<Object> locks = null;

    /**
     * 转换器
     * 
     */
    Converter<T, Integer> converter = null;

    /**
     * 构造函数
     * 
     * @param size 锁数量
     */
    public LockImpl(String name, Integer size, Converter<T, Integer> converter) {
        // 记录锁名
        this.name = name;

        // 构造锁实体
        this.locks = Lists.newArrayListWithCapacity(size);
        while (this.locks.size() < size) {
            this.locks.add(new Object());
        }

        // 记录转换器
        this.converter = converter;
    }

    @Override
    public Object get(T key) {
        // 转换出Key值
        Integer idx = converter.convert(key);

        // Key值规整
        if (idx < 0) {
            log.error(String.format("转换Lock索引失败(key = %s, idx = %d, size = %d)", key, idx, locks.size()));
            throw new RuntimeException("转换Lock索引失败");
        } else if (idx >= locks.size()) {
            idx = idx % locks.size();
        }

        return locks.get(idx);
    }

    @Override
    public Integer size() {
        return locks.size();
    }

    @Override
    public String name() {
        return name;
    }
}
