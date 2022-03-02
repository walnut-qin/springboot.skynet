package com.kaos.his.controller.impl.inpatient.escort.schedule;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Queues;
import com.kaos.his.controller.impl.inpatient.escort.Lock;
import com.kaos.his.controller.inf.inpatient.escort.schedule.RefreshStateController;
import com.kaos.his.service.inf.inpatient.escort.MainService;
import com.kaos.his.util.helper.ThreadPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshStateControllerImpl implements RefreshStateController {
    /**
     * 公用主线程池
     */
    @Autowired
    ThreadPool threadPool;

    /**
     * 定时任务实体
     */
    @Autowired
    Task task;

    /**
     * 陪护主业务
     */
    @Autowired
    MainService escortMainService;

    @Override
    @Scheduled(cron = "0 0 0,4,12,13,18,19,22 * * ?")
    @Scheduled(cron = "0 0/15 8-11,14-17 * * ?")
    public void run() {
        this.threadPool.execute(this.task);
    }

    /**
     * 定时任务实体
     */
    @Component
    class Task implements Runnable {
        /**
         * 日志模块
         */
        Logger logger = Logger.getLogger(Task.class);

        /**
         * 子任务线程池
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                Lock.stateLock.getSize(),
                Lock.stateLock.getSize(),
                0, TimeUnit.SECONDS,
                Queues.newLinkedBlockingDeque());

        /**
         * 发令枪
         */
        CountDownLatch counter = null;

        /**
         * 计时器
         */
        Stopwatch timer = Stopwatch.createStarted();

        @Override
        public void run() {
            // 重置计时器，开始计时
            this.logger.info("====== Mission Start =====");
            this.timer.reset();
            this.timer.start();

            // 查询所有有效陪护证号
            var escortNos = RefreshStateControllerImpl.this.escortMainService.queryUncanceledEscortNos();
            this.logger.info(String.format("活跃陪护证数量: %d 个", escortNos.size()));

            // 初始化发令枪
            this.counter = new CountDownLatch(escortNos.size());

            // 更新状态
            for (String escortNo : escortNos) {
                this.executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (Lock.stateLock.mapToLock(escortNo)) {
                            escortMainService.updateEscortState(escortNo, null, "server", "定时任务");
                        }
                        // 发令枪倒计时
                        Task.this.counter.countDown();
                    }
                });
            }

            // 结束计时
            try {
                this.counter.await();
            } catch (Exception e) {
                this.logger.error(e.getMessage());
            } finally {
                this.timer.stop();
                this.logger.info(String.format("更新用时: %s", this.timer.toString()));
                this.logger.info("==== Mission Complete ====");
            }
        }
    }
}
