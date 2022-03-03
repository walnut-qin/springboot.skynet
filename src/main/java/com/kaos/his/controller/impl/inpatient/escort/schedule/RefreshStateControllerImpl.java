package com.kaos.his.controller.impl.inpatient.escort.schedule;

import java.util.concurrent.CountDownLatch;

import com.google.common.base.Stopwatch;
import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.impl.inpatient.escort.Lock;
import com.kaos.his.controller.inf.inpatient.escort.schedule.RefreshStateController;
import com.kaos.his.service.inf.inpatient.escort.MainService;
import com.kaos.his.util.ThreadPools;
import com.kaos.his.util.helper.ThreadPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms/inpatient/escort/schedule/state")
public class RefreshStateControllerImpl implements RefreshStateController {
    /**
     * 主线程池，容量 = 1，逻辑外壳
     */
    ThreadPool masterPool = ThreadPools.newThreadPool(1);

    /**
     * 从线程池，容量 = 锁数量，逻辑实体
     */
    ThreadPool slavePool = ThreadPools.newThreadPool(Lock.stateLock.getSize());

    /**
     * 发令枪
     */
    CountDownLatch counter = null;

    /**
     * 计时器
     */
    Stopwatch timer = Stopwatch.createStarted();

    /**
     * 陪护主业务
     */
    @Autowired
    MainService escortMainService;

    @Override
    @Scheduled(cron = "0 0 0,4,12,13,18,19,22 * * ?")
    @Scheduled(cron = "0 0/15 8-11,14-17 * * ?")
    public void run() {
        this.masterPool.execute(new Runnable() {
            /**
             * 日志工具
             */
            Logger logger = Logger.getLogger(this.getClass());

            @Override
            public void run() {
                // 重置计时器，开始计时
                this.logger.info("====== Mission Start =====");
                RefreshStateControllerImpl.this.timer.reset();
                RefreshStateControllerImpl.this.timer.start();

                // 查询所有有效陪护证号
                var escortNos = RefreshStateControllerImpl.this.escortMainService.queryUncanceledEscortNos();
                this.logger.info(String.format("活跃陪护证数量: %d 个", escortNos.size()));

                // 初始化发令枪
                RefreshStateControllerImpl.this.counter = new CountDownLatch(escortNos.size());

                // 更新状态
                for (String escortNo : escortNos) {
                    RefreshStateControllerImpl.this.slavePool.execute(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (Lock.stateLock.mapToLock(escortNo)) {
                                escortMainService.updateEscortState(escortNo, null, "server", "定时任务");
                            }
                            // 发令枪倒计时
                            RefreshStateControllerImpl.this.counter.countDown();
                        }
                    });
                }

                // 结束计时
                try {
                    RefreshStateControllerImpl.this.counter.await();
                } catch (Exception e) {
                    this.logger.error(e.getMessage());
                } finally {
                    RefreshStateControllerImpl.this.timer.stop();
                    this.logger.info(String.format("更新用时: %s", RefreshStateControllerImpl.this.timer.toString()));
                    this.logger.info("==== Mission Complete ====");
                }
            }
        });
    }

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = MediaType.JSON)
    public Object show() {
        View view = new View();
        view.master = this.masterPool.show();
        view.slave = this.slavePool.show();
        return view;
    }

    /**
     * 线程池视图
     */
    public static class View {
        /**
         * 主线程池
         */
        ThreadPool.View master = null;

        /**
         * 从线程池
         */
        ThreadPool.View slave = null;
    }
}
