package com.kaos.his.util.helper;

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
    View show();

    /**
     * 线程池视图
     */
    public static class View {
        /**
         * 核心线程池数量
         */
        public Integer coreSize = null;

        /**
         * 最大线程池数量
         */
        public Integer maxSize = null;

        /**
         * 活跃线程池数量
         */
        public Integer activeSize = null;

        /**
         * 等待队列容量
         */
        public Integer queueSize = null;

        /**
         * 任务数量
         */
        public Long taskCount = null;

        /**
         * 已完成的任务数量
         */
        public Long completeTaskCount = null;
    }
}
