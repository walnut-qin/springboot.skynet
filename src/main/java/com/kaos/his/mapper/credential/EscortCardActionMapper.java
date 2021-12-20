package com.kaos.his.mapper.credential;

import java.util.List;

import com.kaos.his.entity.credential.EscortCardAction;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortCardActionMapper {
    /**
     * 查询特定陪护证的出入记录列表
     * 
     * @param escortNo 陪护证编号
     * @return 查询到的出入记录列表
     */
    List<EscortCardAction> QueryEscortCardActions(String escortNo);

    /**
     * 插入一条新的状态记录
     * 
     * @param newAction
     * @return
     */
    int InsertEscortCardAction(EscortCardAction newAction);
}
