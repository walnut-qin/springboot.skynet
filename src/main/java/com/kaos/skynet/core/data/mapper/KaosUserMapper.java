package com.kaos.skynet.core.data.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kaos.skynet.core.data.entity.KaosUser;

@DS("his")
public interface KaosUserMapper {
    /**
     * 检索账户
     * 
     * @param uid 账户ID
     * @return
     */
    KaosUser queryKaosUser(String uid);
}
