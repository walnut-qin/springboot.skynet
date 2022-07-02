package com.kaos.skynet.core.api.data.entity;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KaosUserAccess {
    /**
     * 编码
     */
    private String userCode;

    /**
     * 密码(密文)
     */
    private String password;

    /**
     * token掩码，修改后旧token失效
     */
    private String tokenMask;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosUserAccess) {
            var that = (KaosUserAccess) arg0;
            return StringUtils.equals(this.userCode, that.userCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userCode);
    }
}
