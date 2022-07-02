package com.kaos.skynet.core.api.data.entity;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KaosUserRole {
    /**
     * 编码
     */
    private String userCode;

    /**
     * 角色
     */
    private String role;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosUserRole) {
            var that = (KaosUserRole) arg0;
            return StringUtils.equals(this.userCode, that.userCode)
                    && StringUtils.equals(this.role, that.role);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userCode, role);
    }
}
