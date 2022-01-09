package com.kaos.his.mapper.surgery;

import com.kaos.his.entity.surgery.Room;

import org.springframework.stereotype.Repository;

@Repository
public interface RoomMapper {
    /**
     * 主键查询
     * @param roomId
     * @return
     */
    Room queryRoom(String roomId);
}
