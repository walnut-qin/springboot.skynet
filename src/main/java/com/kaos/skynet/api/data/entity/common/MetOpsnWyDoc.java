package com.kaos.skynet.api.data.entity.common;

import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;

import lombok.Data;

@Data
public class MetOpsnWyDoc {
    /**
     * 姓名
     */
    private String emplName = null;

    /**
     * 备注
     */
    private String mark = null;

    /**
     * 科室编码
     */
    private String deptCode = null;

    /**
     * 员工编码
     */
    private String emplCode = null;

    /**
     * 性别
     */
    private SexEnum sex = null;

    /**
     * 有效性
     */
    private ValidEnum valid = null;
}
