package com.kaos.his.service;

import java.util.Date;
import java.util.HashMap;

import com.kaos.his.entity.fee.DayBalance;
import com.kaos.his.mapper.fee.FinIpbBalanceHeadMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;

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
        void Run(String balanceOperCode, Date beginDate, Date endDate);
    }

    public DayBalance QueryDayBalanceData(String balanceOperCode, Date beginDate, Date endDate) {
        // 定义结果集
        DayBalance dayBalance = new DayBalance();

        // 定义cache => 住院号 - 姓名
        HashMap<String, String> nameMap = new HashMap<>();

        // 第四大项
        new IGetDayBalanceDetail() {
            @Override
            public void Run(String balanceOperCode, Date beginDate, Date endDate) {
                // 获取结算头表相关记录
                var balanceHeads = balanceHeadMapper.QueryBalancerFinIpbBalanceHeads(balanceOperCode, beginDate,
                        endDate);

                // 赋值明细
                balanceHeads.forEach((x) -> {
                    var item = new DayBalance.Detail();
                    item.inpatientNo = x.inpatientNo;
                    if (!nameMap.containsKey(x.inpatientNo)) {
                        var inpatient = inpatientMapper.QueryInpatient(x.inpatientNo.substring(4));
                        if (inpatient == null) {
                            nameMap.put(x.inpatientNo, null);
                        } else {
                            nameMap.put(x.inpatientNo, inpatient.name);
                        }
                    }
                    item.name = nameMap.get(x.inpatientNo);
                    item.value = x.totCost;
                    if (item.value != null) {
                        dayBalance.totCost += item.value;
                    }
                    dayBalance.totCosts.add(item);
                });
            }
        }.Run(balanceOperCode, beginDate, endDate);

        return dayBalance;
    }
}
