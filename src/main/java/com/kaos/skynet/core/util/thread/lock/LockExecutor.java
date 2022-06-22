package com.kaos.skynet.core.util.thread.lock;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.util.thread.lock.LockGuardian.Lock;

public final class LockExecutor {
    /**
     * 禁用构造
     */
    private LockExecutor() {
    }

    /**
     * 核心执行逻辑
     * 
     * @param <T>
     * @param it
     * @param Callable
     * @return
     * @throws Exception
     */
    private static <T> T execute(Iterator<Lock> it, Callable<T> callable) throws Exception {
        if (it.hasNext()) {
            // 获取锁
            var lock = it.next();

            // 执行
            synchronized (lock) {
                return execute(it, callable);
            }
        } else {
            return callable.call();
        }
    }

    /**
     * 带锁执行callable
     * 
     * @param <T>
     * @param locks
     * @param Callable
     * @return
     * @throws Exception
     */
    public static <T> T execute(List<Lock> locks, Callable<T> callable) throws Exception {
        return execute(locks.iterator(), callable);
    }

    /**
     * 带锁执行callable
     * 
     * @param <T>
     * @param lock
     * @param Callable
     * @return
     * @throws Exception
     */
    public static <T> T execute(Lock lock, Callable<T> callable) throws Exception {
        return execute(Lists.newArrayList(lock), callable);
    }

    /**
     * 核心执行逻辑
     * 
     * @param <T>
     * @param it
     * @param Callable
     * @return
     * @throws Exception
     */
    private static void execute(Iterator<Lock> it, Runnable runnable) {
        if (it.hasNext()) {
            // 获取锁
            var lock = it.next();

            // 执行
            synchronized (lock) {
                execute(it, runnable);
            }
        } else {
            runnable.run();
        }
    }

    /**
     * 带锁执行runnable
     * 
     * @param locks
     * @param runnable
     * @throws Exception
     */
    public static void execute(List<Lock> locks, Runnable runnable) {
        execute(locks.iterator(), runnable);
    }

    /**
     * 带锁执行runnable
     * 
     * @param lock
     * @param runnable
     * @throws Exception
     */
    public static void execute(Lock lock, Runnable runnable) {
        execute(Lists.newArrayList(lock), runnable);
    }
}
