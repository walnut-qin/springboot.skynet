package com.kaos.his.controller.inpatient.reconciliate;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.kaos.his.service.inpatient.ReConciliateService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/inpatient/reconciliate")
public class ReConciliateController {
    /**
     * 接口：日志服务
     */
    Logger logger = Logger.getLogger(ReConciliateController.class);

    /**
     * 陪护证服务
     */
    @Autowired
    ReConciliateService reConciliateService;

    @RequestMapping(value = "checkInpatientIncome", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String checkInpatientIncome(@NotNull(message = "开始时间不能为空") Date beginDate,
            @NotNull(message = "结束时间不能为空") Date endDate) {
        // 记录日志
        this.logger.info(String.format("HIS和用友系统住院收入对账"));

        // 调用业务
        this.reConciliateService.checkInpatientIncome(beginDate, endDate);

        return "检查完毕, 请查看日志";
    }
}
