package com.kaos.his.service;

import java.util.Date;
import java.util.HashMap;
import java.util.function.ToDoubleFunction;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.kaos.his.entity.fee.DayBalance;
import com.kaos.his.entity.fee.FinIpbBalanceHead;
import com.kaos.his.mapper.fee.FinIpbBalanceHeadMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayBalanceService {
    /**
     * 结算头表接口
     */
    @Autowired
    FinIpbBalanceHeadMapper balanceHeadMapper;

    @Autowired
    InpatientMapper inpatientMapper;

    interface IGetDayBalanceDetail {
        void Run();
    }

    public Pair<DayBalance, HashMap<String, DayBalance>> QueryDayBalanceData(String balanceOperCode, Date beginDate,
            Date endDate) {
        // 定义结果集
        var rs = new Pair<DayBalance, HashMap<String, DayBalance>>(new DayBalance(), new HashMap<>());

        // 从结算头表中计算所有结算的患者
        var balanceHeads = this.balanceHeadMapper.QueryBalancerFinIpbBalanceHeads(balanceOperCode, beginDate, endDate);
        for (FinIpbBalanceHead balanceHead : balanceHeads) {
            // 初始化结果集
            rs.getValue1().put(balanceHead.inpatientNo, new DayBalance());

            // 第四大项
            new IGetDayBalanceDetail() {
                @Override
                public void Run() {
                    // 填入总和
                    if (rs.getValue0().totCost == null) {
                        rs.getValue0().totCost = balanceHead.totCost;
                    } else {
                        rs.getValue0().totCost += balanceHead.totCost;
                    }

                    // 填入明细
                    var item = rs.getValue1().get(balanceHead.inpatientNo);
                    item.totCost = balanceHead.totCost;
                }
            }.Run();

            // 第五大项
        }

        return rs;
    }

    /**
     * 查询结算员某个时间段内，某种医保的统筹总额
     * 
     * @param balancer  结算员
     * @param pactCode  医保类型
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public Double queryBalancerPubCosts(String balancer, String pactCode, Date beginDate, Date endDate) {
        // 从结算头表中计算所有结算的患者
        var balanceHeads = this.balanceHeadMapper.QueryBalancerFinIpbBalanceHeads(balancer, beginDate, endDate);

        // 过滤医保类型
        var collects = Collections2.filter(balanceHeads, new Predicate<FinIpbBalanceHead>() {
            @Override
            public boolean apply(@Nullable FinIpbBalanceHead input) {
                if (input.pactCode.equals(pactCode)) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        // 求和
        var sum = collects.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            public double applyAsDouble(FinIpbBalanceHead value) {
                return value.pubCost;
            };
        }).sum();

        return sum;
    }

    /**
     * 查询结算员某个时间段内，某种医保的统筹总额
     * 
     * @param balancer  结算员
     * @param pactCode  医保类型
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public Double queryBalancerPayCosts(String balancer, String pactCode, Date beginDate, Date endDate) {
        // 从结算头表中计算所有结算的患者
        var balanceHeads = this.balanceHeadMapper.QueryBalancerFinIpbBalanceHeads(balancer, beginDate, endDate);

        // 过滤医保类型
        var collects = Collections2.filter(balanceHeads, new Predicate<FinIpbBalanceHead>() {
            @Override
            public boolean apply(@Nullable FinIpbBalanceHead input) {
                if (input.pactCode == pactCode) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        // 求和
        var sum = collects.stream().mapToDouble(new ToDoubleFunction<FinIpbBalanceHead>() {
            public double applyAsDouble(FinIpbBalanceHead value) {
                return value.payCost;
            };
        }).sum();

        return sum;
    }
}
