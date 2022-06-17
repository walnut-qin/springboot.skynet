package com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;

import lombok.Builder;
import lombok.Getter;

public interface FinIpbBalanceHeadMapper {
    /**
     * 主键查询
     * 
     * @param invoiceNo 发票号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param transType 交易类型；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinIpbBalanceHead queryBalanceHead(String invoiceNo, TransTypeEnum transType);

    /**
     * 批量查询
     * 
     * @param key
     * @return
     */
    List<FinIpbBalanceHead> queryBalanceHeads(Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * 住院流水号
         */
        private String inpatientNo;

        /**
         * 结算员
         */
        private String balanceOperCode;

        /**
         * 结算开始时间
         */
        private LocalDateTime beginBalanceDate;

        /**
         * 结算结束时间
         */
        private LocalDateTime endBalanceDate;

        /**
         * 医保编码
         */
        private String pactCode;

        /**
         * 日结编号
         */
        private String dayBalanceNo;
    }
}
