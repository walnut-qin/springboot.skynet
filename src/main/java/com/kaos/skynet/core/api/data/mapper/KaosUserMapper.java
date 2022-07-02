package com.kaos.skynet.core.api.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kaos.skynet.core.api.data.entity.KaosUser;

@DS("his")
public interface KaosUserMapper {
    /**
     * 主键检索
     * 
     * @param userCode
     * @return
     */
    KaosUser queryKaosUser(String userCode);
}
