package com.kaos.skynet.core.data.entity;

import lombok.Getter;

/**
 * 账户信息
 */
@Getter
public class KaosUser {
    /**
     * 用户号
     */
    private String uid;

    /**
     * 用户密码
     */
    private String passwd;

    /**
     * 有效标识
     */
    private Boolean valid;
}
