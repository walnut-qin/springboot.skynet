package com.kaos.skynet.core.type.thread.lock;

import java.util.concurrent.Callable;

public interface LockExecutor {
    /**
     * 关联目标锁
     * 
     * @param lock
     */
    LockExecutor link(Lock lock);

    /**
     * 执行内容
     * 
     * @param <T>
     * @param runnable
     * @return
     */
    <T> T execute(Callable<T> returnable);

    /**
     * 执行内容
     * 
     * @param <T>
     * @param runnable
     * @return
     */
    void execute(Runnable runnable);
}
