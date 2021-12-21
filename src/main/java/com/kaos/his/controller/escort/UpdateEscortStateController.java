package com.kaos.his.controller.escort;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.service.EscortService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class UpdateEscortStateController {
    /**
     * 陪护证业务
     */
    @Autowired
    EscortService escortService;

    /**
     * 陪护证操作锁
     */
    private List<Object> escortNoLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 获取陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "updateEscortState", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void UpdateEscortState(@RequestParam("escortNo") String escortNo, @RequestParam("newState") EscortStateEnum newState) {
        // 入参检查
        if (escortNo == null || escortNo.isEmpty()) {
            throw new InvalidParameterException("陪护证号不能为空");
        }

        // 加陪护号锁
        var idx = Integer.valueOf(escortNo.substring(escortNo.length() - 2)) % escortNoLocks.size();
        var lock = this.escortNoLocks.get(idx);
        synchronized (lock) {
            // 执行更新服务
            this.escortService.UpdateEscortState(escortNo, newState);
        }
    }

    /**
     * 定期更新活跃的陪护证状态
     */
    @Scheduled(initialDelay = 5 * 60 * 1000, fixedDelay = 5 * 60 * 1000)
    public void AutoRefreshEscortState() {
        // 获取日志工具
        var logger = Logger.getLogger(UpdateEscortStateController.class.getName());

        // 从数据库中查询所有活跃着的陪护证编号
        var escortCards = this.escortService.QueryAllRegisteredEscortNo();

        // 记录开始日志
        logger.info(String.format(">> 开始更新，总计 %d 条活跃陪护证", escortCards.size()));
        var beginDate = new Date();

        // 轮训刷新状态
        for (EscortCard escortCard : escortCards) {
            try {
                // 加陪护号锁
                var escortNo = escortCard.escortNo;
                var idx = Integer.valueOf(escortNo.substring(escortNo.length() - 2)) % escortNoLocks.size();
                var lock = this.escortNoLocks.get(idx);
                synchronized (lock) {
                    // 执行更新服务
                    this.escortService.RefreshEscortState(escortCard.escortNo);
                }
            } catch (Exception e) {
                logger.error(String.format("xx 更新陪护证 %s 的状态时发生错误: %s", escortCard.escortNo, e.getMessage()));
            }
        }

        // 记录日志
        var endDate = new Date();
        logger.info(String.format("<< 实际更新 %d，耗时 %d ms", escortCards.size(), endDate.getTime() - beginDate.getTime()));
    }
}
