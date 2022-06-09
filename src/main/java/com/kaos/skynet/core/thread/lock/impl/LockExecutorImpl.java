package com.kaos.skynet.core.thread.lock.impl;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Callable;

import com.google.common.collect.Queues;
import com.kaos.skynet.core.thread.lock.Lock;
import com.kaos.skynet.core.thread.lock.LockExecutor;

import lombok.extern.log4j.Log4j;

@Log4j
public class LockExecutorImpl implements LockExecutor {
    /**
     * 保存关联的锁
     */
    Queue<Lock> locks = Queues.newLinkedBlockingQueue();

    @Override
    public LockExecutor link(Lock lock) {
        locks.add(lock);
        return this;
    }

    private <T> T execute(Iterator<Lock> it, Callable<T> Callable) {
        if (it.hasNext()) {
            // 获取锁
            var lock = it.next();

            // 执行
            synchronized (lock.get()) {
                try {
                    log.info(String.format("加锁(hash = %d)", lock.hashCode()));
                    return execute(it, Callable);
                } finally {
                    log.info(String.format("解锁(hash = %d)", lock.hashCode()));
                }
            }
        } else {
            try {
                return Callable.call();
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <T> T execute(Callable<T> Callable) {
        return execute(locks.iterator(), Callable);
    }

    @Override
    public void execute(Runnable runnable) {
        execute(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                runnable.run();
                return null;
            }
        });
    }
}
