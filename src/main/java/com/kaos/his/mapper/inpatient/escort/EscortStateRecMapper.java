package com.kaos.his.mapper.inpatient.escort;

import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortStateRec;

public interface EscortStateRecMapper {
    /**
     * 读取初始状态
     */
    EscortStateRec queryInitState(String escortNo);

    /**
     * 读取当前状态
     */
    EscortStateRec queryCurState(String escortNo);

    /**
     * 读取状态列表
     * 
     * @param escortNo
     * @return
     */
    List<EscortStateRec> queryStates(String escortNo);

    /**
     * 插入新状态
     * 
     * @param escortStateRec
     * @return
     */
    int insertState(EscortStateRec escortStateRec);
}
