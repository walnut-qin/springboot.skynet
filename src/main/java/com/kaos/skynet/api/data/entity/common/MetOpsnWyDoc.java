package com.kaos.skynet.api.data.entity.common;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MetOpsnWyDoc {
    /**
     * 姓名
     */
    private String emplName;

    /**
     * 备注
     */
    private String mark;

    /**
     * 科室编码
     */
    private String deptCode;

    /**
     * 员工编码
     */
    private String emplCode;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 有效性
     */
    private ValidEnum valid;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof MetOpsnWyDoc) {
            var that = (MetOpsnWyDoc) arg0;
            return StringUtils.equals(this.emplCode, that.emplCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emplCode);
    }
}
