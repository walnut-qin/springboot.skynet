package com.kaos.skynet.api.logic.controller.inpatient.escort;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.logic.controller.inpatient.escort.entity.EscortLock;
import com.kaos.skynet.api.logic.controller.inpatient.escort.entity.EscortPool;
import com.kaos.skynet.api.logic.service.inpatient.escort.EscortService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.util.thread.lock.LockExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@PassToken
@Log4j
@RestController
@RequestMapping("/api/inpatient/escort/schedule")
public class ScheduleController {
    /**
     * 陪护证线程池
     */
    @Autowired
    EscortPool escortPool;

    /**
     * 陪护锁
     */
    @Autowired
    EscortLock escortLock;

    /**
     * 陪护证主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 陪护证服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 定时更新系统内尚未注销的陪护证状态
     */
    @ApiName("刷新所有陪护证状态")
    @RequestMapping(value = "updateState", method = RequestMethod.POST, produces = MediaType.JSON)
    @Scheduled(cron = "0 5/10 * * * ?")
    public void updateState() {
        // 检索尚且有效的陪护证
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(
                EscortMainInfoMapper.Key.builder()
                        .states(Lists.newArrayList(
                                StateEnum.无核酸检测结果,
                                StateEnum.等待院内核酸检测结果,
                                StateEnum.等待院外核酸检测结果审核,
                                StateEnum.生效中,
                                StateEnum.其他))
                        .build());

        // 定期任务日志
        log.info(String.format("定期任务: 刷新系统陪护证状态, 数量: %d", escortInfos.size()));

        // 刷新状态
        for (EscortMainInfo escortMainInfo : escortInfos) {
            escortPool.getStateMgr().execute(() -> {
                var lock = escortLock.getStateLock().grant(escortMainInfo.getEscortNo());
                LockExecutor.execute(lock, () -> {
                    try {
                        escortService.updateState(escortMainInfo.getEscortNo(), null, "schedule", null);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                });
            });
        }
    }

    /**
     * 展示线程池状态
     * 
     * @return
     */
    @ApiName("查询陪护线程池状态")
    @RequestMapping(value = "showPoolState", method = RequestMethod.POST, produces = MediaType.JSON)
    public Object showPoolState() {
        return escortPool.show();
    }
}
