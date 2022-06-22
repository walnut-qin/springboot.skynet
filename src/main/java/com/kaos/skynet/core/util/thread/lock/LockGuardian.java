package com.kaos.skynet.core.util.thread.lock;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class LockGuardian {
    /**
     * 锁名
     */
    @Getter
    String name;

    /**
     * 锁对象
     */
    List<Lock> locks;

    /**
     * 构造锁
     * 
     * @param 锁名称
     * @param 锁容量
     */
    public LockGuardian(String name, Integer size) {
        this.name = name;
        this.locks = Lists.newArrayListWithCapacity(size);
        for (int i = 0; i < size; i++) {
            this.locks.add(new Lock(i));
        }
    }

    /**
     * 将对象映射到锁
     * 
     * @param key
     * @return
     */
    public Lock grant(Object key) {
        // 通过hash方法变换到有效索引
        Integer idx = key.hashCode() & Integer.MAX_VALUE % locks.size();

        // 定位对应的锁
        return locks.get(idx);
    }

    /**
     * 锁芯
     */
    @Getter
    @AllArgsConstructor
    public class Lock {
        /**
         * 锁的实际位置
         */
        Integer pos;
    }
}
