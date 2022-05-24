package com.kaos.skynet.api.service.impl.inpatient.fee;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.kaos.skynet.api.data.mapper.outpatient.fee.FinOprPayModelMapper;
import com.kaos.skynet.api.enums.common.TransTypeEnum;
import com.kaos.skynet.api.enums.inpatient.fee.balance.BalanceStateEnum;
import com.kaos.skynet.api.mapper.inpatient.fee.FinIpbInPrepayMapper;
import com.kaos.skynet.api.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.skynet.api.service.inf.inpatient.fee.PrepayService;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrepayServiceImpl implements PrepayService {
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(PrepayServiceImpl.class);

    /**
     * 预交金接口
     */
    @Autowired
    FinIpbInPrepayMapper inPrepayMapper;

    /**
     * 线上实付表接口
     */
    @Autowired
    FinOprPayModelMapper payModelMapper;

    /**
     * 结算头表接口
     */
    @Autowired
    FinIpbBalanceHeadMapper balanceHeadMapper;

    @Override
    @Transactional
    public Map<Integer, Pair<Double, Double>> fixPrepayCost(String patientNo) {
        // 检索最近一次结算记录
        var balances = this.balanceHeadMapper.queryBalancesInPatient(String.format("ZY01%s", patientNo));
        Collections.sort(balances, (x, y) -> {
            return x.balanceNo.compareTo(y.balanceNo);
        });
        var lastBalance = balances.get(balances.size() - 1);
        if (lastBalance.transType != TransTypeEnum.Negative) {
            throw new RuntimeException("患者当前不是召回状态");
        }

        // 查询未结算的预交金
        var prepays = this.inPrepayMapper.queryPrepays(String.format("ZY01%s", patientNo));
        if (prepays == null || prepays.isEmpty()) {
            throw new RuntimeException("未查询到预交金记录");
        }

        // 过滤出未结算的，线上支付的预交金
        var unbalancedPrepays = Collections2.filter(prepays, x -> {
            if (x == null || x.balanceState != BalanceStateEnum.未结算) {
                return false;
            } else if (x.referNum == null || x.referNum.isEmpty()) {
                return false;
            }
            return true;
        });

        // 构造响应
        Map<Integer, Pair<Double, Double>> ret = Maps.newHashMap();

        // 依次修改
        for (var unbalancedPrepay : unbalancedPrepays) {
            // 查询对应的跑批中间表
            var refNum = unbalancedPrepay.referNum;
            var payModels = payModelMapper.queryPayModels(FinOprPayModelMapper.Key.builder()
                .patientId(patientNo)
                .referNo(refNum)
                .invoiceNo(lastBalance.invoiceNo).build());

            // 计算新的预交金值
            var oldCost = unbalancedPrepay.prepayCost;
            var newCost = unbalancedPrepay.prepayCost;
            for (var payModel : payModels) {
                newCost += payModel.getAmt();
            }

            // 修改预交金的值
            this.inPrepayMapper.updatePrepayCost(unbalancedPrepay.inpatientNo, unbalancedPrepay.happenNo, newCost);

            // 记录
            ret.put(unbalancedPrepay.happenNo, new Pair<>(oldCost, newCost));
            this.logger.info(String.format("修改预交金: <住院号 = %s, 序号 = %s, 原预交金 = %s, 现预交金 = %s>", patientNo,
                    unbalancedPrepay.happenNo, oldCost, newCost));
        }

        return ret;
    }
}
