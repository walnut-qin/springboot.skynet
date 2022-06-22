package com.kaos.skynet.api.logic.controller.inpatient.escort.entity;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kaos.skynet.core.util.thread.pool.ThreadPool;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class EscortPool {
    /**
     * 核心线程池
     */
    ThreadPool stateMgr = new ThreadPool("陪护证状态管理线程池", 20);

    /**
     * 显示线程池状态
     * 
     * @return
     */
    public Object show() {
        Map<String, Object> result = Maps.newConcurrentMap();
        result.put(stateMgr.getName(), stateMgr.show());
        return result;
    }
}
