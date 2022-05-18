package com.kaos.skynet.core.thread.pool.impl;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Queues;
import com.kaos.skynet.core.thread.pool.ThreadPool;

/**
 * 线程池封装
 */
public class ThreadPoolImpl implements ThreadPool {
    /**
     * 线程池实体
     */
    ThreadPoolExecutor executor = null;

    /**
     * 构造如下特性的线程池：1. 最大线程数量 = 1.2 * 核心线程数量 .. 下取整；2. 空闲期2小时；3. 无界阻塞队列
     * 
     * @param coreThreadSize 核心线程数量
     */
    public ThreadPoolImpl(Integer coreThreadSize) {
        Integer coreSize = coreThreadSize;
        Integer maxSize = Double.valueOf(Math.floor(1.2d * coreThreadSize)).intValue();
        this.executor = new ThreadPoolExecutor(coreSize, maxSize, 2, TimeUnit.HOURS, Queues.newLinkedBlockingDeque());
    }

    @Override
    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }

    @Override
    public PoolState show() {
        PoolState view = new PoolState();
        view.setCoreSize(this.executor.getCorePoolSize());
        view.setMaxSize(this.executor.getMaximumPoolSize());
        view.setActiveSize(this.executor.getActiveCount());
        view.setQueueSize(this.executor.getQueue().size());
        view.setTaskCount(this.executor.getTaskCount());
        view.setCompleteTaskCount(this.executor.getCompletedTaskCount());
        return view;
    }
}
