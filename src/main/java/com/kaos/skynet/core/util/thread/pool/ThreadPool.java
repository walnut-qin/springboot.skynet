package com.kaos.skynet.core.util.thread.pool;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Queues;

import lombok.Builder;
import lombok.Getter;

/**
 * 无状态线程池
 */
public class ThreadPool {
    /**
     * 线程池名称
     */
    @Getter
    final String name;

    /**
     * 线程池实体
     */
    final ThreadPoolExecutor executor;

    /**
     * 构造线程池
     * 
     * @param name
     * @param threadSize
     */
    public ThreadPool(String name, Integer threadCount) {
        this.name = name;
        this.executor = new ThreadPoolExecutor(threadCount, threadCount,
                0, TimeUnit.SECONDS,
                Queues.newLinkedBlockingDeque());
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }

    /**
     * 展示线程池状态
     */
    public PoolState show() {
        var builder = PoolState.builder();
        builder.name(name);
        builder.size(this.executor.getCorePoolSize());
        builder.activeSize(this.executor.getActiveCount());
        builder.queueSize(this.executor.getQueue().size());
        builder.taskCount(this.executor.getTaskCount());
        builder.completeTaskCount(this.executor.getCompletedTaskCount());
        return builder.build();
    }

    /**
     * 线程池视图
     */
    @Builder
    static class PoolState {
        /**
         * 线程池名称
         */
        String name;

        /**
         * 核心线程池数量
         */
        Integer size;

        /**
         * 活跃线程池数量
         */
        Integer activeSize;

        /**
         * 等待队列容量
         */
        Integer queueSize;

        /**
         * 任务数量
         */
        Long taskCount;

        /**
         * 已完成的任务数量
         */
        Long completeTaskCount;
    }
}
