package com.kaos.his.mapper;

import com.kaos.his.entity.credential.Escort;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortMapper {
    /**
     * 获取陪护证
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    Escort GetEscort(String escortNo);
}
