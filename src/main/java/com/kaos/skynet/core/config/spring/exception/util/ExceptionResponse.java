package com.kaos.skynet.core.config.spring.exception.util;

/**
 * 特殊响应，用于异常传递
 */
public interface ExceptionResponse {
    /**
     * 获取响应码
     * 
     * @return
     */
    Integer getCode();

    /**
     * 获取响应消息
     */
    String getMessage();
}
