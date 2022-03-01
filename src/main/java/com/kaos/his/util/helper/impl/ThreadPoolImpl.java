package com.kaos.his.util.helper.impl;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Queues;
import com.kaos.his.util.helper.ThreadPool;

import org.springframework.stereotype.Component;

@Component
public class ThreadPoolImpl implements ThreadPool {
    /**
     * 线程池实体
     * 
     * @param 核心线程数 40
     * @param 最大线程数 40（等于核心线程数是为了让所有线程成为核心线程，不受空闲时间影响）
     * @param 最大空闲期 0s（不影响核心线程）
     * @param 等待队列  无界阻塞队列
     */
    ThreadPoolExecutor executor = new ThreadPoolExecutor(40, 40, 0, TimeUnit.SECONDS, Queues.newLinkedBlockingDeque());

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
