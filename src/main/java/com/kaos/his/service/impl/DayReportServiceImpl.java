package com.kaos.his.service.impl;

import java.util.function.ToDoubleFunction;

import com.google.common.base.Optional;
import com.kaos.his.entity.inpatient.balance.FinIpbBalanceHead;
import com.kaos.his.entity.inpatient.balance.dayreport.FinIpbDayReportDetail;
import com.kaos.his.mapper.inpatient.balance.FinIpbBalanceHeadMapper;
import com.kaos.his.mapper.inpatient.balance.dayreport.FinIpbDayReportDetailMapper;
import com.kaos.his.mapper.inpatient.balance.dayreport.FinIpbDayReportMapper;
import com.kaos.his.service.DayReportService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DayReportServiceImpl implements DayReportService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DayReportServiceImpl.class.getName());

    /**
     * 日结头表接口
     */
    @Autowired
    FinIpbBalanceHeadMapper finIpbBalanceHeadMapper;

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

    @Transactional
    @Override
    public void fixNewYbDayReportData(String statNo) {
        // 查询到结算实体
        var rpt = this.finIpbDayReportMapper.queryDayReport(statNo);
        if (rpt == null) {
            this.logger.info(String.format("未查询到日结实体(statNo = %s)", statNo));
            return;
        }

        // 查询日结员关联的所有结算记录
        var balances = this.finIpbBalanceHeadMapper.queryBalanceHeadsInBalancer(rpt.rptEmplCode, rpt.beginDate,
                rpt.endDate, "18");
        if (balances == null || balances.isEmpty()) {
            this.logger.info(String.format("结算员无新医保相关结算记录(balancer = %s)", rpt.rptEmplCode));
            return;
        } else {
            this.logger.info(String.format("结算员新医保相关结算记录(balancer = %s):", rpt.rptEmplCode));
            for (var balance : balances) {
                this.logger.info(String.format("住院号 = %s, 统筹 = %f, 账户 = %f", balance.inpatientNo, balance.pubCost,
                        balance.payCost));
            }
        }

        // 计算统筹总额
        var pubCost = balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            @Override
            public double applyAsDouble(FinIpbBalanceHead arg0) {
                return Optional.fromNullable(arg0.pubCost).or(0.0);
            }
        }).sum();
        var payCost = balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            @Override
            public double applyAsDouble(FinIpbBalanceHead arg0) {
                return Optional.fromNullable(arg0.payCost).or(0.0);
            }
        }).sum();

        // 修改统筹数据
        var pubRpt = this.finIpbDayReportDetailMapper.queryDayReportDetail(rpt.statNo, "市直医保_门诊");
        if (pubRpt == null) {
            this.finIpbDayReportDetailMapper.insertDayReportDetail(new FinIpbDayReportDetail() {
                {
                    statNo = rpt.statNo;
                    statCode = "市直医保_门诊";
                    totCost = pubCost;
                }
            });
            this.logger.info(String.format("插入统筹数据(statNo = %s, statCode = %s, totCost = %f)", rpt.statNo, "市直医保_门诊",
                    pubCost));
        } else {
            this.finIpbDayReportDetailMapper.updateDayReportDetail(rpt.statNo, "市直医保_门诊", pubCost);
            this.logger.info(String.format("修改统筹数据(statNo = %s, statCode = %s, totCost = %f)", rpt.statNo, "市直医保_门诊",
                    pubCost));
        }

        // 修改账户数据
        var payRpt = this.finIpbDayReportDetailMapper.queryDayReportDetail(rpt.statNo, "市直统筹市直外伤");
        if (payRpt == null) {
            this.finIpbDayReportDetailMapper.insertDayReportDetail(new FinIpbDayReportDetail() {
                {
                    statNo = rpt.statNo;
                    statCode = "市直统筹市直外伤";
                    totCost = payCost;
                }
            });
            this.logger.info(String.format("插入账户数据(statNo = %s, statCode = %s, totCost = %f)", rpt.statNo, "市直统筹市直外伤",
                    payCost));
        } else {
            this.finIpbDayReportDetailMapper.updateDayReportDetail(rpt.statNo, "市直统筹市直外伤", payCost);
            this.logger.info(String.format("修改账户数据(statNo = %s, statCode = %s, totCost = %f)", rpt.statNo, "市直统筹市直外伤",
                    payCost));
        }
    }
}
