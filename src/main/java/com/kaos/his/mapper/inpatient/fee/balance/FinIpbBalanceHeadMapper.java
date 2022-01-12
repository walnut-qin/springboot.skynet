package com.kaos.his.mapper.inpatient.fee.balance;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.his.enums.TransTypeEnum;

public interface FinIpbBalanceHeadMapper {
    /**
     * 主键查询
     * 
     * @param invoiceNo
     * @param transType
     * @return
     */
    FinIpbBalanceHead queryBalanceHead(String invoiceNo, TransTypeEnum transType);

    /**
     * 以结算员的角度查询结算头表信息
     * 
     * @param balanceOperCode
     * @param beginDate
     * @param endDate
     * @param pactCode
     * @return
     */
    List<FinIpbBalanceHead> queryBalanceHeadsInBalancer(String balancer, Date beginDate, Date endDate, String pactCode);
}
