package com.kaos.his.controller;

import java.util.Date;

import com.kaos.his.service.SurgeryService;
import com.kaos.util.GsonHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class SurgeryController {
    @Autowired
    Logger logger;

    /**
     * 手术服务
     */
    @Autowired
    SurgeryService surgeryService;

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String queryDeptSurgeries(@RequestParam("deptCode") String deptCode,
            @RequestParam("beginDate") Date beginDate,
            @RequestParam("endDate") Date endDate) {
        // 入参检查
        if (deptCode == null || deptCode.isEmpty()) {
            throw new RuntimeException("科室编码不能为空");
        } else if (beginDate == null || endDate == null) {
            throw new RuntimeException("起止时间不能为空");
        }

        // 记录日志
        this.logger.info(String.format("查询科室手术(deptCode = %s, beginDate = %s, endDate = %s)", deptCode,
                beginDate.toString(), endDate.toString()));

        // 执行业务
        var rs = this.surgeryService.queryMetOpsApplies(deptCode, beginDate, endDate);

        return GsonHelper.ToJson(rs);
    }
}
