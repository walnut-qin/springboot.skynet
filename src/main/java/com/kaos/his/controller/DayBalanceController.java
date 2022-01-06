package com.kaos.his.controller;

import java.security.InvalidParameterException;
import java.util.Date;

import com.kaos.his.service.DayBalanceService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class DayBalanceController {
    /**
     * 日志实体
     */
    Logger logger = Logger.getLogger(DayBalanceController.class.getName());

    /**
     * 日结服务
     */
    @Autowired
    DayBalanceService dayBalanceService;

    /**
     * 查询日结统筹部分
     * 
     * @param balancer
     * @param pactCode
     * @param beginDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "dayBalance/queryBalancerPubCost", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String queryBalancerPubCost(@RequestParam("balancer") String balancer,
            @RequestParam("pactCode") String pactCode, @RequestParam("beginDate") Date beginDate,
            @RequestParam("endDate") Date endDate) {
        // 入参检查
        if (balancer == null || balancer.isEmpty()) {
            throw new InvalidParameterException("结算员不能为空");
        } else if (pactCode == null || pactCode.isEmpty()) {
            throw new InvalidParameterException("医保类型不能为空");
        } else if (beginDate == null) {
            throw new InvalidParameterException("开始时间不能为空");
        } else if (endDate == null) {
            throw new InvalidParameterException("结束时间不能为空");
        }

        // 记录日志
        this.logger.info(
                String.format("查询结算员日结数据的统筹部分(balancer = %s, pactCode = %s, beginDate = %s, endDate = %s)", balancer,
                        pactCode, beginDate.toString(), endDate.toString()));

        // 执行业务
        var sum = this.dayBalanceService.queryBalancerPubCosts(balancer, pactCode, beginDate, endDate);

        return sum.toString();
    }

    /**
     * 查询账户消费部分
     * 
     * @param balancer
     * @param pactCode
     * @param beginDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "dayBalance/queryBalancerPayCost", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String queryBalancerPayCost(@RequestParam("balancer") String balancer,
            @RequestParam("pactCode") String pactCode, @RequestParam("beginDate") Date beginDate,
            @RequestParam("endDate") Date endDate) {
        // 入参检查
        if (balancer == null || balancer.isEmpty()) {
            throw new InvalidParameterException("结算员不能为空");
        } else if (pactCode == null || pactCode.isEmpty()) {
            throw new InvalidParameterException("医保类型不能为空");
        } else if (beginDate == null) {
            throw new InvalidParameterException("开始时间不能为空");
        } else if (endDate == null) {
            throw new InvalidParameterException("结束时间不能为空");
        }

        // 记录日志
        this.logger.info(
                String.format("查询结算员日结数据的医保账户部分(balancer = %s, pactCode = %s, beginDate = %s, endDate = %s)", balancer,
                        pactCode, beginDate.toString(), endDate.toString()));

        // 执行业务
        var sum = this.dayBalanceService.queryBalancerPayCosts(balancer, pactCode, beginDate, endDate);

        return sum.toString();
    }
}
