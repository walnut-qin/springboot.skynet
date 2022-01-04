package com.kaos.his.service;

import java.util.Date;
import java.util.HashMap;

import com.kaos.his.entity.fee.DayBalance;
import com.kaos.his.entity.fee.FinIpbBalanceHead;
import com.kaos.his.mapper.fee.FinIpbBalanceHeadMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;

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
}
