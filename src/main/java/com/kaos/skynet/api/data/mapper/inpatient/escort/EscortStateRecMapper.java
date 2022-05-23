package com.kaos.skynet.api.data.mapper.inpatient.escort;

import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;

public interface EscortStateRecMapper {
    /**
     * 查询陪住状态清单
     * 
     * @param escortNo
     * @return
     */
    List<EscortStateRec> queryEscortStateRecs(String escortNo);

    /**
     * 插入新状态
     * 
     * @param escortStateRec 状态实体
     * @return
     */
    Integer insertEscortStateRec(EscortStateRec escortStateRec);
}
