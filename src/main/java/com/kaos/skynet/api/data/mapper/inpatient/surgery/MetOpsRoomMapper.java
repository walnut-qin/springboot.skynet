package com.kaos.skynet.api.data.mapper.inpatient.surgery;

import com.kaos.skynet.api.data.entity.inpatient.surgery.MetOpsRoom;

public interface MetOpsRoomMapper {
    /**
     * 主键查询
     * 
     * @param roomId 手术间编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOpsRoom queryMetOpsRoom(String roomId);
}
