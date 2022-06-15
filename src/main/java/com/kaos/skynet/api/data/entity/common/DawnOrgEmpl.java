package com.kaos.skynet.api.data.entity.common;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 实体：职员（DAWN_ORG_EMPL）
 */
@Getter
@Setter
@Builder
public class DawnOrgEmpl {
    /**
     * 工号
     */
    private String emplCode;

    /**
     * 职工姓名
     */
    private String emplName;

    /**
     * 姓名拼音码
     */
    private String emplNameSpellCode;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 职务
     */
    private PositionEnum position;

    /**
     * 职级
     */
    private RankEnum rank;

    /**
     * 人员类型
     */
    private EmplTypeEnum emplType;

    /**
     * 身份证号
     */
    private String identityCardNo;

    /**
     * 归属科室编码
     */
    private String deptCode;

    /**
     * 归属护士站编码
     */
    private String nurseCellCode;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 备注
     */
    private String remark;

    /**
     * 有效标识
     */
    private ValidEnum valid;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof DawnOrgEmpl) {
            var that = (DawnOrgEmpl) arg0;
            return StringUtils.equals(this.emplCode, that.emplCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(emplCode);
    }

    /**
     * 职务
     */
    @AllArgsConstructor
    public static enum PositionEnum implements Enum {
        院长("1", "院长"),
        主任("2", "主任"),
        科长("3", "科长"),
        科员("4", "科员");

        /**
         * 数据库存值
         */
        @Getter
        private String value;

        /**
         * 描述存值
         */
        @Getter
        private String description;
    }

    /**
     * 职级
     */
    @AllArgsConstructor
    public static enum RankEnum implements Enum {
        特殊津贴专家("1", "特殊津贴专家"),
        主任医师("2", "主任医师"),
        副主任医师("3", "副主任医师"),
        主治医师("4", "主治医师"),
        医师("5", "医师"),
        见习医师("6", "见习医师"),
        副主任护师("7", "副主任护师"),
        主管护师("8", "主管护师"),
        护师("9", "护师");

        /**
         * 数据库存值
         */
        @Getter
        private String value;

        /**
         * 描述存值
         */
        @Getter
        private String description;
    }

    /**
     * 职工类型
     */
    @AllArgsConstructor
    public static enum EmplTypeEnum implements Enum {
        所有("A", "所有"),
        厨师("C", "厨师"),
        医生("D", "医生"),
        收款员("F", "收款员"),
        护士("N", "护士"),
        其他("O", "其他"),
        药师("P", "药师"),
        技师("T", "技师");

        /**
         * 数据库存值
         */
        @Getter
        private String value;

        /**
         * 描述存值
         */
        @Getter
        private String description;
    }
}
