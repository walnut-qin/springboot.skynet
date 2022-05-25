package com.kaos.skynet.core.type.thread.pool.impl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Queues;
import com.kaos.skynet.core.type.thread.pool.ThreadPool;

import lombok.extern.log4j.Log4j;

/**
 * 线程池封装
 */
@Log4j
public class ThreadPoolImpl implements ThreadPool {
    /**
     * 线程池名称
     */
    String name = null;

    /**
     * 线程池实体
     */
    ThreadPoolExecutor executor = null;

    /**
     * 计数器
     */
    CountDownLatch counter = null;

    /**
     * 计时器
     */
    final Stopwatch timer = Stopwatch.createUnstarted();

    /**
     * 构造如下特性的线程池：1. 最大线程数量 = 1.2 * 核心线程数量 .. 下取整；2. 空闲期2小时；3. 无界阻塞队列
     * 
     * @param name           线程池名称
     * @param coreThreadSize 核心线程数量
     */
    public ThreadPoolImpl(String name, Integer coreThreadSize) {
        this.name = name;
        Integer coreSize = coreThreadSize;
        Integer maxSize = Double.valueOf(Math.floor(1.2d * coreThreadSize)).intValue();
        this.executor = new ThreadPoolExecutor(coreSize, maxSize, 2, TimeUnit.HOURS, Queues.newLinkedBlockingDeque());
    }

    /**
     * 构造线程池
     * 
     * @param name     线程池名
     * @param executor 线程池处理器
     */
    public ThreadPoolImpl(String name, ThreadPoolExecutor executor) {
        this.name = name;
        this.executor = executor;
    }

    @Override
    public void monitor(Integer cnt) {
        if (timer.isRunning()) {
            log.error("试图在监控期间再次启动监控器, 存在逻辑错误");
            throw new RuntimeException("线程池逻辑错误");
        } else {
            // 重置计数器
            counter = new CountDownLatch(cnt);
            // 启动计时器
            timer.reset();
            timer.start();
            // 记录日志
            log.info(String.format("启动线程池(%s)监控器, 任务数量 %d", name, cnt));
        }
    }

    @Override
    public void execute(Runnable runnable) {
        this.executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } catch (Exception e) {
                    // 若发生异常，则记录异常，但不影响线程运行
                    log.error(String.format("线程池(%s)执行任务时发生异常(%s)", name, e.getMessage()));
                } finally {
                    if (counter != null) {
                        counter.countDown();
                    }
                }
            }
        });
    }

    @Override
    public void await() {
        if (timer.isRunning()) {
            try {
                counter.await();
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                timer.stop();
                log.info(String.format("线程池(%s)已完成所有任务, 耗时 %s", name, timer.toString()));
            }
        } else {
            log.error("试图等待尚未启动的监控器, 存在逻辑错误");
            throw new RuntimeException("线程池逻辑错误");
        }
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
