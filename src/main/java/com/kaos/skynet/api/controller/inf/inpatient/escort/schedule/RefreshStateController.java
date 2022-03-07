package com.kaos.skynet.api.controller.inf.inpatient.escort.schedule;

public interface RefreshStateController {
    /**
     * 更新所有仍然有效的陪护状态
     */
    void run();

    /**
     * 展示线程池状态
     * 
     * @return
     */
    Object show();
}
