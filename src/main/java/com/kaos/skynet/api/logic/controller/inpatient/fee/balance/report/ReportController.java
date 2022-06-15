package com.kaos.skynet.api.logic.controller.inpatient.fee.balance.report;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.kaos.skynet.api.data.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.skynet.api.logic.controller.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping({ "/api/inpatient/fee/balance/report", "/ms/inpatient/fee/balance/report", "/ms/inpatient/dayreport" })
public class ReportController {
    /**
     * 费用明细表
     */
    @Autowired
    FinIpbBalanceHeadMapper balanceHeadMapper;

    /**
     * 查询统筹金额
     * 
     * @param balancer
     * @param beginDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "queryNewYbPubCost", method = RequestMethod.GET, produces = MediaType.JSON)
    public Double queryNewYbPubCost(@NotNull(message = "结算员不能为空") String balancer,
            @NotNull(message = "开始时间不能为空") LocalDateTime beginDate,
            @NotNull(message = "结束时间不能为空") LocalDateTime endDate) {
        // 记录日志
        log.info("查询新医保统筹日结");

        // 检索所有结算记录
        var keyBuilder = FinIpbBalanceHeadMapper.Key.builder();
        keyBuilder.balanceOperCode(balancer);
        keyBuilder.beginBalanceDate(beginDate);
        keyBuilder.endBalanceDate(endDate);
        keyBuilder.pactCode("18");
        var balances = balanceHeadMapper.queryBalanceHeads(keyBuilder.build());

        // 算和
        return balances.stream().mapToDouble(x -> {
            return Optional.fromNullable(x.getPubCost()).or(0.0);
        }).sum();
    }

    /**
     * 查询统筹金额
     * 
     * @param balancer
     * @param beginDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "queryNewYbPayCost", method = RequestMethod.GET, produces = MediaType.JSON)
    public Double queryNewYbPayCost(@NotNull(message = "结算员不能为空") String balancer,
            @NotNull(message = "开始时间不能为空") LocalDateTime beginDate,
            @NotNull(message = "结束时间不能为空") LocalDateTime endDate) {
        // 记录日志
        log.info("查询新医保账户日结");

        // 检索所有结算记录
        var keyBuilder = FinIpbBalanceHeadMapper.Key.builder();
        keyBuilder.balanceOperCode(balancer);
        keyBuilder.beginBalanceDate(beginDate);
        keyBuilder.endBalanceDate(endDate);
        keyBuilder.pactCode("18");
        var balances = balanceHeadMapper.queryBalanceHeads(keyBuilder.build());

        // 算和
        return balances.stream().mapToDouble(x -> {
            return Optional.fromNullable(x.getPayCost()).or(0.0);
        }).sum();
    }
}
