package com.kaos.skynet.core.api.data.entity;

import com.google.common.base.Objects;
import com.kaos.skynet.core.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KaosRole {
    /**
     * 键值
     */
    private String key;

    /**
     * 描述
     */
    private String value;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosRole) {
            var that = (KaosRole) arg0;
            return StringUtils.equals(this.key, that.key);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }
}
