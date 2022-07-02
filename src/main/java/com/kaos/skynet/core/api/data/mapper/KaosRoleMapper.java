package com.kaos.skynet.core.api.data.mapper;

import com.kaos.skynet.core.api.data.entity.KaosRole;

public interface KaosRoleMapper {
    /**
     * 主键检索
     * 
     * @param key
     * @return
     */
    KaosRole queryKaosRole(String key);
}
