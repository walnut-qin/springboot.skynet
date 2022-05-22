package com.kaos.skynet.core.type.thread.lock.impl;

import java.util.Iterator;
import java.util.Queue;

import com.google.common.collect.Queues;
import com.kaos.skynet.core.type.Returnable;
import com.kaos.skynet.core.type.thread.lock.Lock;
import com.kaos.skynet.core.type.thread.lock.LockExecutor;

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

    private void execute(Iterator<Lock> it, Runnable runnable) {
        if (it.hasNext()) {
            // 获取锁
            var lock = it.next();

            // 执行
            synchronized (lock) {
                try {
                    log.debug(String.format("加锁(name = %s, hash = %d)", lock.name(), lock.hashCode()));
                    execute(it, runnable);
                } finally {
                    log.debug(String.format("解锁(name = %s, hash = %d)", lock.name(), lock.hashCode()));
                }
            }
        } else {
            runnable.run();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        execute(locks.iterator(), runnable);
    }

    private <T> T execute(Iterator<Lock> it, Returnable<T> returnable) {
        if (it.hasNext()) {
            // 获取锁
            var lock = it.next();

            // 执行
            synchronized (lock) {
                try {
                    log.debug(String.format("加锁(hash = %d)", lock.hashCode()));
                    return execute(it, returnable);
                } finally {
                    log.debug(String.format("解锁(hash = %d)", lock.hashCode()));
                }
            }
        } else {
            return returnable.run();
        }
    }

    @Override
    public <T> T execute(Returnable<T> returnable) {
        return execute(locks.iterator(), returnable);
    }
}
