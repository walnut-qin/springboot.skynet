package com.kaos.skynet.api.logic.controller.inpatient.escort.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kaos.skynet.core.thread.Threads;
import com.kaos.skynet.core.thread.pool.ThreadPool;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class EscortPool {
    /**
     * 定时任务主线程池
     */
    final ThreadPool guardPool = Threads.newGuardThreadPool("陪护证守护线程池");

    /**
     * 子线程池
     */
    final ThreadPool taskPool = Threads.newThreadPool("陪护证线程池", 20);

    /**
     * 显示线程池状态
     * 
     * @return
     */
    public Map<String, ThreadPool.PoolState> show() {
        Map<String, ThreadPool.PoolState> result = Maps.newConcurrentMap();
        result.put("guardPool", guardPool.show());
        result.put("taskPool", taskPool.show());
        return result;
    }
}
