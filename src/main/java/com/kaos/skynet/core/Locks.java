package com.kaos.skynet.core;

import com.kaos.skynet.core.thread.lock.LockExecutor;
import com.kaos.skynet.core.thread.lock.LockFactory;
import com.kaos.skynet.core.thread.lock.impl.LockExecutorImpl;
import com.kaos.skynet.core.thread.lock.impl.LockFactoryImpl;

public final class Locks {
    /**
     * 构造指定容量的匿名锁
     * 
     * @param size
     * @return
     */
    public static LockFactory newLockFactory(String name, Integer size) {
        return new LockFactoryImpl(name, size);
    }

    /**
     * 构造指定容量的匿名锁
     * 
     * @param size
     * @return
     */
    public static LockFactory newLockFactory(Integer size) {
        return newLockFactory("匿名锁", size);
    }

    /**
     * 构造运行器
     */
    public static LockExecutor newLockExecutor() {
        return new LockExecutorImpl();
    }
}
