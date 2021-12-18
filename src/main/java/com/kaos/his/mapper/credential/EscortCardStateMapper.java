package com.kaos.his.mapper.credential;

import java.util.List;

import com.kaos.his.entity.credential.EscortCardState;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortCardStateMapper {
    /**
     * 查询特定陪护证的状态列表
     * 
     * @param escortNo 陪护证编号
     * @return 查询到的状态记录列表
     */
    public List<EscortCardState> QueryEscortCardStates(String escortNo);

    /**
     * 插入一条新的状态记录
     * 
     * @param escortNo 陪护证编号
     * @param state    新状态
     * @param remark   备注
     * @return
     */
    public int InsertEscortCardState(EscortCardState newState);
}
