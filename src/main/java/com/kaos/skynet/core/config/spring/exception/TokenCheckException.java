package com.kaos.skynet.core.config.spring.exception;

import lombok.Getter;

/**
 * 自定义异常：Token校验异常
 */
public class TokenCheckException extends RuntimeException {
    /**
     * 响应码
     */
    @Getter
    private Integer code;

    /**
     * 构造函数
     * 
     * @param code
     * @param message
     */
    public TokenCheckException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     * 
     * @param message
     */
    public TokenCheckException(String message) {
        this(-1, message);
    }
}
