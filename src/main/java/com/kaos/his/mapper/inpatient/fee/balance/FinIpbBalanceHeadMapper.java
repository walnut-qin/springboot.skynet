package com.kaos.his.mapper.inpatient.fee.balance;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.his.enums.common.TransTypeEnum;

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
     * 以结算员的角度查询结算头表信息
     * 
     * @param balanceOperCode 结算员；等于 {@code null} 时，不作为判断条件
     * @param beginDate       开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate         结束时间；等于 {@code null} 时，不作为判断条件
     * @param pactCode        医保编码；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<FinIpbBalanceHead> queryBalanceHeadsInBalancer(String balancer, Date beginDate, Date endDate, String pactCode);
}
