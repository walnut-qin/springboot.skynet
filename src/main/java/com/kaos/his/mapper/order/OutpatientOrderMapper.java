package com.kaos.his.mapper.order;

import java.util.List;

import com.kaos.his.entity.order.OutpatientOrder;

import org.springframework.stereotype.Repository;

@Repository
public interface OutpatientOrderMapper {
    /**
     * 根据医嘱号查询医嘱
     * 
     * @param moOrder 医嘱号，主键
     * @return
     */
    OutpatientOrder QueryOutpatientOrder(String moOrder);

    /**
     * 查询符合条件的医嘱
     * 
     * @param indexNo   索引号
     * @param itemCode  项目编码
     * @param dayOffset 时间偏移量
     * @return
     */
    List<OutpatientOrder> QueryOutpatientOrders(String indexNo, String itemCode, Integer dayOffset);
}
