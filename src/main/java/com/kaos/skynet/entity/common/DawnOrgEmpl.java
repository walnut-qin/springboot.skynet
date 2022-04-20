package com.kaos.skynet.entity.common;

import java.sql.Date;

import com.kaos.skynet.enums.impl.common.EmplTypeEnum;
import com.kaos.skynet.enums.impl.common.PositionEnum;
import com.kaos.skynet.enums.impl.common.RankEnum;
import com.kaos.skynet.enums.impl.common.SexEnum;
import com.kaos.skynet.enums.impl.common.ValidStateEnum;

/**
 * 实体：职员（DAWN_ORG_EMPL）
 */
public class DawnOrgEmpl {
    /**
     * 工号
     */
    public String emplCode = null;

    /**
     * 职工姓名
     */
    public String emplName = null;

    /**
     * 姓名拼音码
     */
    public String emplNameSpellCode = null;

    /**
     * 性别
     */
    public SexEnum sex = null;

    /**
     * 生日
     */
    public Date birthday = null;

    /**
     * 职务
     */
    public PositionEnum position = null;

    /**
     * 职级
     */
    public RankEnum rank = null;

    /**
     * 人员类型
     */
    public EmplTypeEnum emplType = null;

    /**
     * 身份证号
     */
    public String identityCardNo = null;

    /**
     * 归属科室编码
     */
    public String deptCode = null;

    /**
     * 归属护士站编码
     */
    public String nurseCellCode = null;

    /**
     * 电子邮件
     */
    public String email = null;

    /**
     * 电话号码
     */
    public String tel = null;

    /**
     * 备注
     */
    public String remark = null;

    /**
     * 有效标识
     */
    public ValidStateEnum valid = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 归属科室
         */
        public DawnOrgDept dept = null;

        /**
         * 归属护士站
         */
        public DawnOrgDept nurceCell = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}
