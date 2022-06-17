package com.kaos.skynet.api.data.his.entity.common.config;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigMultiMap {
    /**
     * 开关名
     */
    private String name = null;

    /**
     * 开关值
     */
    private String value = null;

    /**
     * 有效标识
     */
    private ValidEnum valid = null;

    /**
     * 备注
     */
    private String remark = null;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof ConfigMultiMap) {
            var that = (ConfigMultiMap) arg0;
            return StringUtils.equals(this.name, that.name) && StringUtils.equals(this.value, that.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, value);
    }
}
