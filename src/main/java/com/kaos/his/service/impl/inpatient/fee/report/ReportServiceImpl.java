package com.kaos.his.service.impl.inpatient.fee.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.ToDoubleFunction;

import com.google.common.base.Optional;
import com.kaos.his.cache.Cache;
import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.his.entity.inpatient.fee.balance.dayreport.FinIpbDayReportDetail;
import com.kaos.his.enums.impl.common.DeptOwnEnum;
import com.kaos.his.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.his.mapper.inpatient.fee.balance.dayreport.FinIpbDayReportDetailMapper;
import com.kaos.his.mapper.inpatient.fee.balance.dayreport.FinIpbDayReportMapper;
import com.kaos.his.service.inf.inpatient.fee.report.ReportService;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    Cache<String, DawnOrgEmpl> emplCache;

    /**
     * 科室cache
     */
    @Autowired
    Cache<String, DawnOrgDept> deptCache;

    @Override
    public Double queryNewYbPubCost(String balancer, Date beginDate, Date endDate) {
        // 检索所有结算记录
        var balances = this.balanceHeadMapper.queryBalancesInBalancer(balancer, beginDate, endDate, "18");

        // 算和
        return balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            @Override
            public double applyAsDouble(FinIpbBalanceHead arg0) {
                return Optional.fromNullable(arg0.pubCost).or(0.0);
            }
        }).sum();
    }

    @Override
    public Double queryNewYbPayCost(String balancer, Date beginDate, Date endDate) {
        // 检索所有结算记录
        var balances = this.balanceHeadMapper.queryBalancesInBalancer(balancer, beginDate, endDate, "18");

        // 算和
        return balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            @Override
            public double applyAsDouble(FinIpbBalanceHead arg0) {
                return Optional.fromNullable(arg0.payCost).or(0.0);
            }
        }).sum();
    }

    /**
     * 以独立事务更新指定的日结
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void fixNewYbDayReportData(String statNo) {
        // 查询到结算实体
        var rpt = this.finIpbDayReportMapper.queryDayReport(statNo);
        if (rpt == null) {
            this.logger.info(String.format("未查询到日结实体(statNo = %s)", statNo));
            return;
        }

        // 查询日结员关联的所有结算记录
        Pair<Double, Double> cost = new Pair<Double, Double>(0d, 0d);
        var balances = this.balanceHeadMapper.queryBalancesInDayReport(rpt.statNo, "18");
        for (var balance : Optional.fromNullable(balances).or(new ArrayList<>())) {
            this.logger.info(String.format("发票号 = %s, 住院号 = %s, 统筹 = %f, 账户 = %f", balance.invoiceNo,
                    balance.inpatientNo, balance.pubCost, balance.payCost));
            cost = cost.setAt0(cost.getValue0() + balance.pubCost);
            cost = cost.setAt1(cost.getValue1() + balance.payCost);
        }

        // 修改统筹数据
        var pubRpt = this.finIpbDayReportDetailMapper.queryDayReportDetail(rpt.statNo, "市直医保_门诊");
        if (pubRpt == null) {
            var reportDetail = new FinIpbDayReportDetail();
            reportDetail.statNo = rpt.statNo;
            reportDetail.statCode = "市直医保_门诊";
            reportDetail.totCost = cost.getValue0();
            this.finIpbDayReportDetailMapper.insertDayReportDetail(reportDetail);
            this.logger.info(String.format("插入统筹数据(statNo = %s, statCode = %s, totCost = %f)", reportDetail.statNo,
                    reportDetail.statCode, reportDetail.totCost));
        } else {
            this.finIpbDayReportDetailMapper.updateDayReportDetail(rpt.statNo, "市直医保_门诊", cost.getValue0());
            this.logger.info(String.format("修改统筹数据(statNo = %s, statCode = %s, totCost = %f)", rpt.statNo, "市直医保_门诊",
                    cost.getValue0()));
        }

        // 修改账户数据
        var payRpt = this.finIpbDayReportDetailMapper.queryDayReportDetail(rpt.statNo, "市直统筹市直外伤");
        if (payRpt == null) {
            var reportDetail = new FinIpbDayReportDetail();
            reportDetail.statNo = rpt.statNo;
            reportDetail.statCode = "市直统筹市直外伤";
            reportDetail.totCost = cost.getValue1();
            this.finIpbDayReportDetailMapper.insertDayReportDetail(reportDetail);
            this.logger.info(String.format("插入账户数据(statNo = %s, statCode = %s, totCost = %f)", reportDetail.statNo,
                    reportDetail.statCode, reportDetail.totCost));
        } else {
            this.finIpbDayReportDetailMapper.updateDayReportDetail(rpt.statNo, "市直统筹市直外伤", cost.getValue1());
            this.logger.info(String.format("修改账户数据(statNo = %s, statCode = %s, totCost = %f)", rpt.statNo, "市直统筹市直外伤",
                    cost.getValue1()));
        }
    }

    /**
     * 批量更新
     */
    @Override
    @Transactional
    public void fixNewYbDayReportData(Date beginDate, Date endDate, DeptOwnEnum deptOwn) {
        // 查询目标范围内的所有日结记录
        var rpts = this.finIpbDayReportMapper.queryDayReprots(beginDate, endDate, deptOwn);

        // 每一个日结单独启动会话刷新
        for (var rpt : rpts) {
            try {
                // 记录日志
                this.logger.info(String.format("修改新医保日结数据(statNo = %s)", rpt.statNo));

                // 以独立的事务运行
                this.selfService.fixNewYbDayReportData(rpt.statNo);
            } catch (Exception e) {
                this.logger.error(String.format("更新数据异常(statNo = %s)", rpt.statNo));
            }
        }
    }
}
