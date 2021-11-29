package com.kaos.his.mapper.order;

import java.util.List;

import com.kaos.his.entity.order.InpatientOrder;

public interface InpatientOrderMapper {
    /**
     * 根据医嘱号查询医嘱
     * 
     * @param moOrder 医嘱号，主键
     * @return
     */
    InpatientOrder QueryInpatientOrder(String moOrder);

    /**
     * 查询符合条件的医嘱
     * 
     * @param patientNo 住院号
     * @param itemCode  项目编码
     * @param dayOffset 时间偏移量
     * @return
     */
    List<InpatientOrder> QueryInpatientOrders(String patientNo, String itemCode, Integer dayOffset);
}
