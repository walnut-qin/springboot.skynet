package com.kaos.his.mapper.inpatient.surgery;

import com.kaos.his.entity.inpatient.surgery.MetOpsRoom;

public interface MetOpsRoomMapper {
    /**
     * 主键查询
     * 
     * @param roomId
     * @return
     */
    MetOpsRoom queryMetOpsRoom(String roomId);
}
