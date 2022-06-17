package com.kaos.skynet.api.data.his.mapper.inpatient.escort;

import java.util.List;

import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec;

public interface EscortStateRecMapper {
    /**
     * 查询陪住状态清单
     * 
     * @param escortNo
     * @return
     */
    List<EscortStateRec> queryEscortStateRecs(String escortNo);

    /**
     * 查询陪住登记状态
     * 
     * @param escortNo
     * @return
     */
    EscortStateRec queryFirstEscortStateRec(String escortNo);

    /**
     * 查询陪护证最后的状态
     * 
     * @param escortNo
     * @return
     */
    EscortStateRec queryLastEscortStateRec(String escortNo);

    /**
     * 插入新状态
     * 
     * @param escortStateRec 状态实体
     * @return
     */
    Integer insertEscortStateRec(EscortStateRec escortStateRec);
}
