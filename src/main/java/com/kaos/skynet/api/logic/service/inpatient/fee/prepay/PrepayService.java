package com.kaos.skynet.api.logic.service.inpatient.fee.prepay;

import java.util.List;
import java.util.Objects;

import com.kaos.skynet.api.data.his.entity.inpatient.fee.FinIpbInPrepay;
import com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.FinIpbBalanceHead.BalanceStateEnum;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.fee.FinIpbInPrepayMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.skynet.api.data.his.mapper.outpatient.fee.FinOprPayModelMapper;
import com.kaos.skynet.core.json.GsonWrapper;
import com.kaos.skynet.core.type.utils.IntegerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * 预交金服务
 */
@Log4j
@Service
public class PrepayService {
    /**
     * 序列化工具
     */
    GsonWrapper gsonWrapper = new GsonWrapper();

    /**
     * 结算头表接口
     */
    @Autowired
    FinIpbBalanceHeadMapper balanceHeadMapper;

    /**
     * 预交金接口
     */
    @Autowired
    FinIpbInPrepayMapper inPrepayMapper;

    /**
     * 线上支付表接口
     */
    @Autowired
    FinOprPayModelMapper payModelMapper;

    /**
     * 隔日召回后修改预交金
     * 
     * @param patientNo 住院号
     * @return
     */
    @Transactional
    public List<PrepayModifyResult> fixRecallPrepay(String patientNo) {
        // 检索结算记录
        var balanceKeyBuilder = FinIpbBalanceHeadMapper.Key.builder();
        balanceKeyBuilder.inpatientNo("ZY01".concat(patientNo));
        var balanceHeads = balanceHeadMapper.queryBalanceHeads(balanceKeyBuilder.build());
        if (balanceHeads.isEmpty()) {
            log.error("未曾结算, 住院号 = ".concat(patientNo));
            throw new RuntimeException("未曾结算");
        }

        // 获取上次结算记录
        balanceHeads.sort((x, y) -> {
            return IntegerUtils.compare(y.getBalanceNo(), x.getBalanceNo());
        });
        var lastBalanceHead = balanceHeads.get(0);
        if (lastBalanceHead.getTransType() != TransTypeEnum.Negative) {
            log.error("当前并未召回, 住院号 = ".concat(patientNo));
            throw new RuntimeException("当前并未召回");
        }

        // 检索未结算的预交金
        var keyBuilder = FinIpbInPrepayMapper.Key.builder();
        keyBuilder.inpatientNo("ZY01".concat(patientNo));
        keyBuilder.balanceState(BalanceStateEnum.未结算);
        var prepays = inPrepayMapper.queryPrepays(keyBuilder.build());
        if (prepays.isEmpty()) {
            log.error("无未结算的预交金, 住院号 = ".concat(patientNo));
            throw new RuntimeException("无未结算的预交金");
        }

        // 筛选出referNum非空的预交金，这类预交金为线上支付
        var onlinePrepays = prepays.stream().filter(x -> {
            return x.getReferNum() != null;
        });

        // 处理所有线上预交金
        return onlinePrepays.map(x -> {
            // 检索该预交金的退费跑批记录
            var builder = FinOprPayModelMapper.Key.builder();
            builder.patientId(patientNo);
            builder.referNo(x.getReferNum());
            builder.invoiceNo(lastBalanceHead.getInvoiceNo());
            var payModels = payModelMapper.queryPayModels(builder.build());
            if (payModels.isEmpty()) {
                return null;
            }

            // 构造新预交金记录
            var newPrepay = gsonWrapper.clone(x, FinIpbInPrepay.class);
            for (var payModel : payModels) {
                newPrepay.setPrepayCost(newPrepay.getPrepayCost() + payModel.getAmt());
            }

            // 添加结果集
            var itemBuilder = PrepayModifyResult.builder();
            itemBuilder.inPatientNo(newPrepay.getInpatientNo());
            itemBuilder.happenNo(newPrepay.getHappenNo());
            itemBuilder.oldCost(x.getPrepayCost());
            itemBuilder.newCost(newPrepay.getPrepayCost());

            // 修改数据库
            inPrepayMapper.updatePrepay(newPrepay);

            return itemBuilder.build();
        }).filter(Objects::nonNull).toList();
    }

    /**
     * 预交金修改结果
     */
    @Getter
    @Setter
    @Builder
    public static class PrepayModifyResult {
        /**
         * 住院流水号
         */
        private String inPatientNo;

        /**
         * 预交金序号
         */
        private Integer happenNo;

        /**
         * 原值
         */
        private Double oldCost;

        /**
         * 新值
         */
        private Double newCost;
    }
}
