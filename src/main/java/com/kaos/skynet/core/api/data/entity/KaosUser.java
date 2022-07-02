package com.kaos.skynet.core.api.data.entity;

import java.time.LocalDate;

import com.google.common.base.Objects;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 账户信息
 */
@Getter
@Builder
public class KaosUser {
    /**
     * 编码
     */
    private String userCode;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 身份证号
     */
    private String identityNo;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 性别
     */
    private SexEnum sex;

    /**
     * 邮件
     */
    private String email;

    /**
     * 电话
     */
    private String telephone;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof KaosUser) {
            var that = (KaosUser) arg0;
            return StringUtils.equals(this.userCode, that.userCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userCode);
    }

    @Getter
    @AllArgsConstructor
    public enum SexEnum implements Enum {
        Male("M", "男"),
        Female("F", "女"),
        Unknown("U", "不详");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }
}
