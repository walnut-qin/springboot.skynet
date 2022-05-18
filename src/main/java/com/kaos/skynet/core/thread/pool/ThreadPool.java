package com.kaos.skynet.core.thread.pool;

import lombok.Setter;

public interface ThreadPool {
    /**
     * 提交任务
     * 
     * @param runnable 可执行任务实体
     */
    void execute(Runnable runnable);

    /**
     * 展示线程池当前状态
     * 
     * @return
     */
    PoolState show();

    /**
     * 线程池视图
     */
    static class PoolState {
        /**
         * 核心线程池数量
         */
        @Setter
        private Integer coreSize = null;

        /**
         * 最大线程池数量
         */
        @Setter
        private Integer maxSize = null;

        /**
         * 活跃线程池数量
         */
        @Setter
        private Integer activeSize = null;

        /**
         * 等待队列容量
         */
        @Setter
        private Integer queueSize = null;

        /**
         * 任务数量
         */
        @Setter
        private Long taskCount = null;

        /**
         * 已完成的任务数量
         */
        @Setter
        private Long completeTaskCount = null;
    }
}
