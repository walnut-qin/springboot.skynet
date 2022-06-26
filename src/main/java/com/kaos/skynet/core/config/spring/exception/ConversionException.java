package com.kaos.skynet.core.config.spring.exception;

import lombok.Getter;

@Getter
public class ConversionException extends RuntimeException {
    /**
     * 源类型
     */
    private String orgType;

    /**
     * 目标类型
     */
    private String dstType;

    /**
     * 构造函数
     * 
     * @param orgType
     * @param dstType
     * @param message
     */
    public ConversionException(Class<?> orgType, Class<?> dstType, String message) {
        super(message);
        this.orgType = orgType.getName();
        this.dstType = dstType.getName();
    }
}
