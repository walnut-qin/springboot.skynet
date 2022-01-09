package com.kaos.his.entity.common;

import java.util.Date;

import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.ValidStateEnum;

/**
 * 实体：患者信息（XYHIS.COM_PATIENTINFO）
 */
public class Patient {
    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 患者姓名
     */
    public String name = null;

    /**
     * 生日
     */
    public Date birthday = null;

    /**
     * 性别
     */
    public SexEnum sex = null;

    /**
     * 身份证号
     */
    public String identityCardNo = null;

    /**
     * 联系电话
     */
    public String tel = null;

    /**
     * 操作职员
     */
    public String operCode = null;

    /**
     * 操作日期
     */
    public Date operDate = null;

    /**
     * 有效性
     */
    public ValidStateEnum valid = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 基本信息操作员
         */
        public Employee operator = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
