package com.kaos.skynet.api.data.his.entity.common.config;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfigSwitch {
    /**
     * 开关名
     */
    private String name;

    /**
     * 开关值
     */
    private Boolean value;

    /**
     * 有效标识
     */
    private ValidEnum valid;

    /**
     * 备注
     */
    private String remark;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof ConfigSwitch) {
            var that = (ConfigSwitch) arg0;
            return StringUtils.equals(this.name, that.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
