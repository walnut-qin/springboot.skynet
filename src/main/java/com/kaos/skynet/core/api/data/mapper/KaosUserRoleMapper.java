package com.kaos.skynet.core.api.data.mapper;

import java.util.List;

import com.kaos.skynet.core.api.data.entity.KaosUserRole;

import lombok.Builder;
import lombok.Getter;

public interface KaosUserRoleMapper {
    /**
     * 主键检索
     * 
     * @param userCode
     * @param role
     * @return
     */
    KaosUserRole queryKaosUserRole(String userCode, String role);

    /**
     * 多值检索
     * 
     * @param key
     * @return
     */
    List<KaosUserRole> queryKaosUserRoles(Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * 用户编码
         */
        private String userCode;
    }
}
