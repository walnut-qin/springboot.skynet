package com.kaos.his.controller.escort;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
     * 锁资源
     */
    private List<Object> locks = new ArrayList<>() {
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
    public void Run(@RequestParam("escortNo") String escortNo, @RequestParam("newState") EscortStateEnum newState) {
        // 入参检查
        if (escortNo == null || escortNo.isEmpty()) {
            throw new InvalidParameterException("陪护证号不能为空");
        }

        // 执行更新服务
        var lock = this.locks.get(Integer.valueOf(escortNo) % 10);
        synchronized (lock) {
            this.escortService.UpdateEscortState(escortNo, newState);
        }
    }

    /**
     * 定期更新活跃的陪护证状态
     */
    @Scheduled(initialDelay = 2000, fixedDelay = 2000)
    public void AutoRefreshEscortState() {
        // 从数据库中查询所有活跃着的陪护证编号
        var escortCards = this.escortService.QueryAllRegisteredEscortNo();

        // 轮训刷新状态
        for (EscortCard escortCard : escortCards) {
            try {
                var lock = this.locks.get(Integer.valueOf(escortCard.escortNo) % 10);
                synchronized (lock) {
                    this.escortService.RefreshEscortState(escortCard.escortNo);
                }
            } catch (Exception e) {
                Logger.getLogger(UpdateEscortStateController.class.getName()).error(e.getMessage());
            }
        }

        // 记录日志
        Logger.getLogger(UpdateEscortStateController.class.getName())
                .info(String.format("更新了 %d 个陪护证的状态", escortCards.size()));
    }
}
