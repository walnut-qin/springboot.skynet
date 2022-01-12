package com.kaos.his.mapper.inpatient.order;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.order.MetOrdiOrder;

public interface MetOrdiOrderMapper {
    /**
     * 主键查询
     * 
     * @param moOrder
     * @return
     */
    MetOrdiOrder queryInpatientOrder(String moOrder);

    /**
     * 查询医嘱列表
     * 
     * @param inpatientNo
     * @param termId
     * @param beginDate
     * @param endDate
     * @return
     */
    List<MetOrdiOrder> queryInpatientOrders(String inpatientNo, String termId, Date beginDate, Date endDate);
}
