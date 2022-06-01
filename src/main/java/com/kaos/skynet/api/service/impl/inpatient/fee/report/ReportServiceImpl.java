package com.kaos.skynet.api.service.impl.inpatient.fee.report;

import java.util.Date;
import java.util.function.ToDoubleFunction;

import com.google.common.base.Optional;
import com.kaos.skynet.api.data.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.cache.common.DawnOrgEmplCache;
import com.kaos.skynet.api.data.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.skynet.api.data.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.skynet.api.data.mapper.inpatient.fee.balance.report.FinIpbDayReportDetailMapper;
import com.kaos.skynet.api.data.mapper.inpatient.fee.balance.report.FinIpbDayReportMapper;
import com.kaos.skynet.api.service.inf.inpatient.fee.report.ReportService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ReportServiceImpl.class.getName());

    /**
     * 注解自身，用于会话传递
     */
    @Autowired
    ReportService selfService;

    /**
     * 日结头表接口
     */
    @Autowired
    FinIpbBalanceHeadMapper balanceHeadMapper;

    /**
     * 日结接口
     */
    @Autowired
    FinIpbDayReportMapper finIpbDayReportMapper;

    /**
     * 日结明细接口
     */
    @Autowired
    FinIpbDayReportDetailMapper finIpbDayReportDetailMapper;

    /**
     * 职员cache
     */
    @Autowired
    DawnOrgEmplCache emplCache;

    /**
     * 科室cache
     */
    @Autowired
    DawnOrgDeptCache deptCache;

    @Override
    public Double queryNewYbPubCost(String balancer, Date beginDate, Date endDate) {
        // 检索所有结算记录
        // var balances = this.balanceHeadMapper.queryBalancesInBalancer(balancer, beginDate, endDate, "18");
        var balances = this.balanceHeadMapper.queryBalanceHeads(null);

        // 算和
        return balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            @Override
            public double applyAsDouble(FinIpbBalanceHead arg0) {
                return Optional.fromNullable(arg0.getPubCost()).or(0.0);
            }
        }).sum();
    }

    @Override
    public Double queryNewYbPayCost(String balancer, Date beginDate, Date endDate) {
        // 检索所有结算记录
        // var balances = this.balanceHeadMapper.queryBalancesInBalancer(balancer, beginDate, endDate, "18");
        var balances = this.balanceHeadMapper.queryBalanceHeads(null);

        // 算和
        return balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            @Override
            public double applyAsDouble(FinIpbBalanceHead arg0) {
                return Optional.fromNullable(arg0.getPayCost()).or(0.0);
            }
        }).sum();
    }
}
