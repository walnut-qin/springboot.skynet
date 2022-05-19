package com.kaos.skynet.api.mapper.inpatient.order;

import java.util.Date;
import java.util.List;

import com.kaos.skynet.api.entity.inpatient.order.MetOrdiOrder;

public interface MetOrdiOrderMapper {
    /**
     * 主键查询
     * 
     * @param moOrder 医嘱单号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOrdiOrder queryInpatientOrder(String moOrder);

    /**
     * 查询医嘱列表
     * 
     * @param inpatientNo 住院号；等于 {@code null} 时，不作为判断条件
     * @param termId      项目编码；等于 {@code null} 时，不作为判断条件
     * @param beginDate   开始时间；等于 {@code null} 时，不作为判断条件
     * @param endDate     结束时间；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<MetOrdiOrder> queryInpatientOrders(String inpatientNo, String termId, Date beginDate, Date endDate);
}
