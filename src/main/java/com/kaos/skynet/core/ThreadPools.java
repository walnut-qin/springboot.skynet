package com.kaos.skynet.core;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Queues;
import com.kaos.skynet.core.thread.pool.ThreadPool;
import com.kaos.skynet.core.thread.pool.impl.ThreadPoolImpl;

import lombok.extern.log4j.Log4j;

@Log4j
public final class ThreadPools {
    /**
     * 创建守护线程池，仅含1个线程，队列容量也为1
     * 
     * @return
     */
    public static ThreadPool newGuardThreadPool(String name) {
        // 构造特殊线程池
        var executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, Queues.newArrayBlockingQueue(1),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable arg0, ThreadPoolExecutor arg1) {
                        log.warn(String.format("线程池<%s>过载, 丢弃任务", name));
                    }
                });

        // 创建实体
        return new ThreadPoolImpl(name, executor);
    }

    /**
     * 创建指定核心数量的线程池
     * 
     * @param corePoolSize
     * @return
     */
    public static ThreadPool newThreadPool(String name, int corePoolSize) {
        return new ThreadPoolImpl(name, corePoolSize);
    }
}
