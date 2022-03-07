package com.kaos.skynet.mapper.inpatient.escort;

import java.util.List;

import com.kaos.skynet.entity.inpatient.escort.EscortStateRec;

public interface EscortStateRecMapper {
    /**
     * 查询注册时的初始状态
     * 
     * @param escortNo 陪护证号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    EscortStateRec queryInitState(String escortNo);

    /**
     * 查询当前状态
     * 
     * @param escortNo 陪护证号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    EscortStateRec queryCurState(String escortNo);

    /**
     * 查询状态列表
     * 
     * @param escortNo 陪护证号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    List<EscortStateRec> queryStates(String escortNo);

    /**
     * 插入新状态
     * 
     * @param escortStateRec 状态实体
     * @return
     */
    int insertState(EscortStateRec escortStateRec);
}
