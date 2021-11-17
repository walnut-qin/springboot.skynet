package com.kaos.his.entity.personnel;

import java.util.Date;

import com.kaos.his.enums.SexEnum;

/**
 * 市民类
 */
public class Citizen {
    /**
     * 姓名
     */
    public String name;

    /**
     * 性别
     */
    public SexEnum sex;

    /**
     * 生日
     */
    public Date birthday;

    /**
     * 身份证号
     */
    public String identityNo;

    /**
     * 联系电话列表
     */
    public String phoneNo;

    /**
     * 邮箱列表
     */
    public String email;
}
