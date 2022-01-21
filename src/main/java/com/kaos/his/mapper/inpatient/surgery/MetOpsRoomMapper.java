package com.kaos.his.mapper.inpatient.surgery;

import com.kaos.his.entity.inpatient.surgery.MetOpsRoom;

public interface MetOpsRoomMapper {
    /**
     * 主键查询
     * 
     * @param roomId 手术间编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOpsRoom queryMetOpsRoom(String roomId);
}
