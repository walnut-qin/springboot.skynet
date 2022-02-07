package com.kaos.his.service.inpatient.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.ToDoubleFunction;

import com.google.common.base.Optional;
import com.kaos.his.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.his.entity.inpatient.fee.balance.dayreport.FinIpbDayReportDetail;
import com.kaos.his.enums.common.DeptOwnEnum;
import com.kaos.his.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.his.mapper.inpatient.fee.balance.dayreport.FinIpbDayReportDetailMapper;
import com.kaos.his.mapper.inpatient.fee.balance.dayreport.FinIpbDayReportMapper;
import com.kaos.his.service.inpatient.DayReportService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DayReportServiceImpl implements DayReportService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DayReportServiceImpl.class.getName());

    /**
     * 注解自身，用于会话传递
     */
    @Autowired
    DayReportService selfService;

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

    /**
     * 以独立事务更新指定的日结
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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

    /**
     * 批量更新
     */
    @Transactional
    @Override
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

    @Override
    public Double queryNewYbPubCost(String balancer, Date beginDate, Date endDate) {
        // 检索所有结算记录
        var balances = this.finIpbBalanceHeadMapper.queryBalanceHeadsInBalancer(balancer, beginDate, endDate, "18");

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
        var balances = this.finIpbBalanceHeadMapper.queryBalanceHeadsInBalancer(balancer, beginDate, endDate, "18");

        // 算和
        return balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            @Override
            public double applyAsDouble(FinIpbBalanceHead arg0) {
                return Optional.fromNullable(arg0.payCost).or(0.0);
            }
        }).sum();
    }

    @Override
    public void exportNewYbData(Date beginDate, Date endDate, DeptOwnEnum deptOwn) {
        // 结果集
        List<FinIpbBalanceHead> balances = new ArrayList<>();

        // 查询目标时段的所有日结记录
        for (var rpt : this.finIpbDayReportMapper.queryDayReprots(beginDate, endDate, deptOwn)) {
            balances.addAll(this.finIpbBalanceHeadMapper.queryBalanceHeadsInBalancer(rpt.rptEmplCode, rpt.beginDate,
                    rpt.endDate, "18"));
        }

        // 排序
        balances.sort(new Comparator<FinIpbBalanceHead>() {
            @Override
            public int compare(FinIpbBalanceHead arg0, FinIpbBalanceHead arg1) {
                return arg0.inpatientNo.compareTo(arg1.inpatientNo);
            }
        });

        // 逐条打印
        for (var balance : balances) {
            this.logger.info(String.format("inpatientNo = %s, pubCost = %f, payCost = %f", balance.inpatientNo,
                    balance.pubCost, balance.payCost));
        }

        // 汇总
        this.logger.info(String.format("汇总: pubCost = %f, payCost = %f",
                balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
                    @Override
                    public double applyAsDouble(FinIpbBalanceHead arg0) {
                        return arg0.pubCost;
                    }
                }).sum(), balances.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
                    @Override
                    public double applyAsDouble(FinIpbBalanceHead arg0) {
                        return arg0.payCost;
                    }
                }).sum()));
    }

    @Override
    public void checkDayReportData(String statNo) {
        // 检索所有本次日结关联的结算记录
        var balanceHeads = this.finIpbBalanceHeadMapper.queryBalanceHeadsInDayReport(statNo);

        // 计算第四大项综合
        this.logger.info(String.format("第四大项总额 = %f",
                balanceHeads.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
                    @Override
                    public double applyAsDouble(FinIpbBalanceHead arg0) {
                        return arg0.totCost;
                    }
                }).sum()));

        // 轮训输出
        this.logger.info(String.format("总量 = %d", balanceHeads.size()));
        for (FinIpbBalanceHead balanceHead : balanceHeads) {
            this.logger.info(String.format("住院号 = %s", balanceHead.inpatientNo));
        }
    }
}
