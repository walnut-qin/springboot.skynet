package com.kaos.his.controller;

import java.security.InvalidParameterException;

import com.kaos.his.service.DrugHedgingService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class DrugHedgingController {
    /**
     * 日志实体
     */
    Logger logger = Logger.getLogger(DrugHedgingController.class.getName());

    /**
     * 药品对冲服务
     */
    @Autowired
    DrugHedgingService drugHedgingService;

    /**
     * 科室编码
     * 
     * @param deptCode
     * @return
     */
    @RequestMapping(value = "drugHedging/queryType", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String QueryType(@RequestParam("deptCode") String deptCode) {
        // 业务日志
        this.logger.info(String.format("查询科室退药类型(deptCode = %s)", deptCode));

        // 入参检测
        if (deptCode == null || deptCode.isEmpty()) {
            throw new InvalidParameterException("科室编码不能为空");
        }

        return this.drugHedgingService.QueryHedgingType(deptCode);
    }
}
