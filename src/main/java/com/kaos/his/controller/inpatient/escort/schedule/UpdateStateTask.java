package com.kaos.his.controller.inpatient.escort.schedule;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;
import com.kaos.his.controller.inpatient.escort.EscortController;
import com.kaos.his.service.inf.inpatient.escort.EscortService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateStateTask {
    /**
     * 陪护证主表服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 线程池
     */
    ThreadPoolExecutor azusaTask = new ThreadPoolExecutor(1, 1, 2, TimeUnit.HOURS,
            new LinkedBlockingDeque<Runnable>());

    @Scheduled(cron = "0 0 0,4,12,13,18,19,22 * * ?")
    @Scheduled(cron = "0 0/15 8-11,14-17 * * ?")
    public void Run() {
        // 执行业务
        this.azusaTask.execute(new Azusa());
    }

    /**
     * Azusa Task
     */
    class Azusa implements Runnable {
        /**
         * 日志接口
         */
        Logger logger = Logger.getLogger(Azusa.class.getName());

        /**
         * 线程池
         */
        ThreadPoolExecutor missionTask = new ThreadPoolExecutor(EscortController.stateLockHelper.getLockSize(),
                EscortController.stateLockHelper.getLockSize(),
                2, TimeUnit.HOURS,
                new LinkedBlockingDeque<Runnable>());

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
            this.logger.info("====== Mission Start =====");
            // 读取所有尚未注销的陪护证记录
            var escortNos = UpdateStateTask.this.escortService.queryUncanceledEscortNos();

            // 初始化发令枪和计时器
            this.counter = new CountDownLatch(escortNos.size());
            this.timer.reset();

            // 执行任务
            this.logger.info(String.format("活跃陪护证数量: %d 个", escortNos.size()));
            this.timer.start();
            for (String escortNo : escortNos) {
                this.missionTask.execute(new Mission(escortNo));
            }

            // 等待任务完成
            try {
                this.counter.await();
            } catch (Exception e) {
                this.logger.error(e.getMessage());
            } finally {
                this.timer.stop();
            }
            this.logger.info(String.format("更新用时: %s", this.timer.toString()));
            this.logger.info("==== Mission Complete ====");
        }

        /**
         * Mission
         */
        class Mission implements Runnable {
            /**
             * 日志接口
             */
            Logger logger = Logger.getLogger(Mission.class.getName());

            /**
             * 任务实体（陪护证编号）
             */
            String escortNo = null;

            Mission(String escortNo) {
                this.escortNo = escortNo;
            }

            @Override
            public void run() {
                try {
                    synchronized (EscortController.stateLockHelper.mapToLock(this.escortNo)) {
                        // 执行业务
                        UpdateStateTask.this.escortService.updateEscortState(escortNo, null, "server", "定期任务");
                    }
                } catch (Exception ex) {
                    // 记录错误日志
                    this.logger.error(String.format("更新陪护证(%s)的状态失败(%s)", this.escortNo, ex.getMessage()));
                } finally {
                    // 计数器自减
                    Azusa.this.counter.countDown();
                }
            }
        }
    }
}
