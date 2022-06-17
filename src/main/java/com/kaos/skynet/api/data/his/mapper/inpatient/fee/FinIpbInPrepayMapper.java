package com.kaos.skynet.api.data.his.mapper.inpatient.fee;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.fee.FinIpbInPrepay;
import com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.FinIpbBalanceHead.BalanceStateEnum;

import lombok.Builder;
import lombok.Getter;

public interface FinIpbInPrepayMapper {
    /**
     * 查询预交金
     * 
     * @param inpatientNo 住院流水号, 等于 {@code null} 时，不作为条件
     * @return
     */
    List<FinIpbInPrepay> queryPrepays(Key key);

    /**
     * 检索key
     */
    @Getter
    @Builder
    public static class Key {
        /**
         * 住院号
         */
        private String inpatientNo;

        /**
         * 结算状态
         */
        private BalanceStateEnum balanceState;
    }

    /**
     * 变更预交金数值
     * 
     * @param inpatientNo 住院流水号, 等于 {@code null} 时，将IS NULL作为条件
     * @param happenNo    发生序号, 等于 {@code null} 时，将IS NULL作为条件
     * @param cost        预交金金额
     * @return 修改记录数量
     */
    Integer updatePrepay(FinIpbInPrepay inPrepay);
}
