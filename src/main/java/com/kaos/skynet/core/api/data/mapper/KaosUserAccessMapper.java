package com.kaos.skynet.core.api.data.mapper;

import com.kaos.skynet.core.api.data.entity.KaosUserAccess;

public interface KaosUserAccessMapper {
    /**
     * 主键检索
     * 
     * @param userCode
     * @return
     */
    KaosUserAccess queryKaosUserAccess(String userCode);
}
