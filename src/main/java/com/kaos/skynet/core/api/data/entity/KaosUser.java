package com.kaos.skynet.core.api.data.entity;

import lombok.Getter;

/**
 * 账户信息
 */
@Getter
public class KaosUser {
    /**
     * 用户号
     */
    private String uuid;

    /**
     * 用户密码
     */
    private String pwd;

    /**
     * 有效标识
     */
    private Boolean valid;

    /**
     * token掩码，增加token复杂度，修改后，历史token立刻失效
     */
    private String tokenMask;
}
