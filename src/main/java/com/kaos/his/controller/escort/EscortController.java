package com.kaos.his.controller.escort;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.service.EscortService;
import com.kaos.util.GsonHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class EscortController {
    /**
     * 陪护证业务
     */
    @Autowired
    EscortService escortService;

    /**
     * 陪护证号锁
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
     * 患者卡号锁
     */
    private List<Object> patientLocks = new ArrayList<>() {
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
     * 陪护人卡号锁
     */
    private List<Object> helperLocks = new ArrayList<>() {
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
    @RequestMapping(value = "regEscort", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String RegEscort(@RequestParam("patientCardNo") String patientCardNo,
            @RequestParam("helperCardNo") String helperCardNo) {
        // 入参判断
        if (patientCardNo == null || patientCardNo.isEmpty()) {
            throw new InvalidParameterException("患者号不能为空");
        } else if (helperCardNo == null || helperCardNo.isEmpty()) {
            throw new InvalidParameterException("陪护号不能为空");
        }

        // 声明陪护实体
        EscortCard recEscortCard = null;

        // 加患者锁
        var patientIdx = Integer.valueOf(patientCardNo.substring(patientCardNo.length() - 2)) % patientLocks.size();
        var patientLock = this.patientLocks.get(patientIdx);
        synchronized (patientLock) {
            // 加陪护锁
            var helperIdx = Integer.valueOf(helperCardNo.substring(helperCardNo.length() - 2)) % helperLocks.size();
            var helperLock = this.patientLocks.get(helperIdx);
            synchronized (helperLock) {
                // 添加陪护
                recEscortCard = this.escortService.InsertEscort(patientCardNo, helperCardNo);
            }
        }

        return GsonHelper.ToJson(recEscortCard);
    }

    /**
     * 更新陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "updateEscortState", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void UpdateEscortState(@RequestParam("escortNo") String escortNo,
            @RequestParam("newState") EscortStateEnum newState) {
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
     * 定期自动更新陪护证状态
     */
    @Scheduled(initialDelay = 5 * 60 * 1000, fixedDelay = 5 * 60 * 1000)
    public void AutoUpdateEscortState() {
        // 获取日志工具
        var logger = Logger.getLogger(EscortController.class.getName());

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

    /**
     * 为陪护人添加院外陪护证附件
     * 
     * @param cardNo 患者就诊卡号
     * @return
     */
    @RequestMapping(value = "attachAnnex", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void AttachAnnex(@RequestParam("helperCardNo") String helperCardNo, @RequestParam("picUrl") String picUrl) {
        // 入参检查
        if (helperCardNo == null || helperCardNo.isEmpty()) {
            throw new InvalidParameterException("陪护人卡号不能为空");
        }

        // 执行业务
        this.escortService.AttachAnnex(helperCardNo, picUrl);
    }
}
