package com.kaos.his.util.helper.impl;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Queues;
import com.kaos.his.util.helper.ThreadPool;

/**
 * 线程池封装
 */
public class ThreadPoolImpl implements ThreadPool {
    /**
     * 线程池实体
     */
    ThreadPoolExecutor executor = null;

    /**
     * 构造如下特性的线程池：1. 最大线程数量 = 1.2 * 核心线程数量；2. 空闲期2小时；3. 无界阻塞队列
     * 
     * @param threadSize 核心线程数量
     */
    public ThreadPoolImpl(Integer threadSize) {
        Integer coreSize = threadSize;
        Integer maxSize = (int) (1.2 * coreSize);
        this.executor = new ThreadPoolExecutor(coreSize, maxSize, 2, TimeUnit.HOURS, Queues.newLinkedBlockingDeque());
    }

    @Override
    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }

    @Override
    public View show() {
        View view = new View();
        view.coreSize = this.executor.getCorePoolSize();
        view.maxSize = this.executor.getMaximumPoolSize();
        view.activeSize = this.executor.getActiveCount();
        view.queueSize = this.executor.getQueue().size();
        view.taskCount = this.executor.getTaskCount();
        view.completeTaskCount = this.executor.getCompletedTaskCount();
        return view;
    }
}
