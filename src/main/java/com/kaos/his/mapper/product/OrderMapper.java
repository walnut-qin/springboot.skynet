package com.kaos.his.mapper.product;

import com.kaos.his.entity.product.Order;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    /**
     * 根据医嘱号查询医嘱
     * 
     * @param moOrder 医嘱号，主键
     * @return
     */
    Order QueryOrder(String moOrder);
}
