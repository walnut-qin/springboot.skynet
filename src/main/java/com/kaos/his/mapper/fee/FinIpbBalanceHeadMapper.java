package com.kaos.his.mapper.fee;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.fee.FinIpbBalanceHead;
import com.kaos.his.enums.TransTypeEnum;

import org.springframework.stereotype.Repository;

@Repository
public interface FinIpbBalanceHeadMapper {
    /**
     * 主键查询结算头表信息
     * 
     * @param invoiceNo
     * @param transType
     * @return
     */
    FinIpbBalanceHead QueryFinIpbBalanceHead(String invoiceNo, TransTypeEnum transType);

    /**
     * 查询某个结算员某个时段的所有记录
     * 
     * @param balanceOperCode
     * @return0
     * 
     */
    List<FinIpbBalanceHead> QueryBalancerFinIpbBalanceHeads(String balanceOperCode, Date beginDate, Date endDate);
}
