package com.kaos.skynet.core.type.thread.lock.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.type.thread.lock.Lock;
import com.kaos.skynet.core.type.thread.lock.LockFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class LockFactoryImpl implements LockFactory {
    /**
     * 锁名称
     */
    @Getter
    String lockName = null;

    /**
     * 锁成员
     */
    List<Lock> locks = null;

    /**
     * 构造函数
     */
    public LockFactoryImpl(String lockName, Integer lockSize) {
        this.lockName = lockName;
        this.locks = Lists.newArrayListWithCapacity(lockSize);
        for (Integer i = 0; i < lockSize; i++) {
            this.locks.add(new LockImpl(new Object()));
        }
    }

    @Override
    public <K> Lock getLock(K key) {
        // 将key映射为List索引
        Integer idx = key.hashCode() & Integer.MAX_VALUE % locks.size();

        // 构造所对象
        return locks.get(idx);
    }

    @Override
    public Integer size() {
        return locks.size();
    }

    @AllArgsConstructor
    public class LockImpl implements Lock {
        /**
         * 实际的锁实体
         */
        @Getter
        Object lock;

        @Override
        public String getName() {
            return lockName;
        }
    }
}
