package com.kaos.skynet.api.controller.impl.inpatient.escort.schedule;

import java.util.concurrent.CountDownLatch;

import com.google.common.base.Stopwatch;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.impl.inpatient.escort.LockMgr;
import com.kaos.skynet.api.controller.inf.inpatient.escort.schedule.RefreshStateController;
import com.kaos.skynet.api.service.inf.inpatient.escort.EscortService;
import com.kaos.skynet.core.ThreadPools;
import com.kaos.skynet.core.type.thread.pool.ThreadPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms/inpatient/escort/schedule/state")
public class StateControllerImpl implements RefreshStateController {
    /**
     * 主线程池，容量 = 1，逻辑外壳
     */
    ThreadPool masterPool = ThreadPools.newGuardThreadPool("状态监控-管理员");

    /**
     * 从线程池，容量 = 锁数量，逻辑实体
     */
    ThreadPool slavePool = ThreadPools.newThreadPool("状态监控-操作员", LockMgr.stateLock.size());

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
    EscortService escortMainService;

    @Override
    // @Scheduled(cron = "0 0 0,4,12,13,18,19,22 * * ?")
    // @Scheduled(cron = "0 0/15 8-11,14-17 * * ?")
    @Scheduled(cron = "0 0/10 * * * ?")
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
                StateControllerImpl.this.timer.reset();
                StateControllerImpl.this.timer.start();

                // 查询所有有效陪护证号
                var escortNos = StateControllerImpl.this.escortMainService.queryUncanceledEscortNos();
                this.logger.info(String.format("活跃陪护证数量: %d 个", escortNos.size()));

                // 初始化发令枪
                StateControllerImpl.this.counter = new CountDownLatch(escortNos.size());

                // 更新状态
                for (String escortNo : escortNos) {
                    StateControllerImpl.this.slavePool.execute(new Runnable() {
                        /**
                         * 日志句柄
                         */
                        Logger logger = Logger.getLogger(this.getClass());

                        @Override
                        public void run() {
                            synchronized (LockMgr.stateLock.getLock(escortNo).get()) {
                                try {
                                    escortMainService.updateEscortState(escortNo, null, "server", "定时任务");
                                } catch (Exception e) {
                                    logger.error(String.format("更新异常<escortNo=%s,cause=%s>", escortNo, e.getMessage()));
                                }
                            }
                            // 发令枪倒计时
                            StateControllerImpl.this.counter.countDown();
                        }
                    });
                }

                // 结束计时
                try {
                    StateControllerImpl.this.counter.await();
                } catch (Exception e) {
                    this.logger.error(e.getMessage());
                } finally {
                    StateControllerImpl.this.timer.stop();
                    this.logger.info(String.format("更新用时: %s", StateControllerImpl.this.timer.toString()));
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
        ThreadPool.PoolState master = null;

        /**
         * 从线程池
         */
        ThreadPool.PoolState slave = null;
    }
}
