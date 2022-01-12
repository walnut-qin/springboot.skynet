package com.kaos.his.mapper.inpatient.escort;

import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortActionRec;

public interface EscortActionRecMapper {
    /**
     * 读取当前状态
     */
    EscortActionRec queryLastAction(String escortNo);

    /**
     * 读取状态列表
     * 
     * @param escortNo
     * @return
     */
    List<EscortActionRec> queryActions(String escortNo);
}
