package com.kaos.skynet.core.util.lock;

import java.util.List;

import com.google.common.collect.Lists;

public class Lock {
    /**
     * 锁名
     */
    String name;

    /**
     * 锁对象
     */
    List<Object> locks;

    /**
     * 构造锁
     * 
     * @param 锁名称
     * @param 锁容量
     */
    public Lock(String name, Integer size) {
        this.name = name;
        this.locks = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) {
            this.locks.add(new Object());
        }
    }

    /**
     * 将对象映射到锁
     * 
     * @param key
     * @return
     */
    public Object map(Object key) {
        // 通过hash方法变换到有效索引
        Integer idx = key.hashCode() & Integer.MAX_VALUE % locks.size();

        // 定位对应的锁
        return locks.get(idx);
    }

    public class InnerLock {

    }
}
