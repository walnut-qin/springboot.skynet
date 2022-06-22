package com.kaos.skynet.core.spring.net;

import lombok.Getter;

/**
 * 响应封装器
 */
@Getter
public class RspWrapper<T> {
    /**
     * 响应码 0: 成功; 1: 失败
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 私有化构造函数
     */
    private RspWrapper() {
    }

    /**
     * 构造成功响应
     * 
     * @param <T>
     * @param data
     * @return
     */
    public static <T> RspWrapper<T> wrapSuccessResponse(T data) {
        RspWrapper<T> response = new RspWrapper<>();
        response.code = 0;
        response.data = data;
        return response;
    }

    /**
     * 构造失败响应
     * 
     * @param <T>
     * @param message
     * @return
     */
    public static <T> RspWrapper<T> wrapFailResponse(String message) {
        RspWrapper<T> response = new RspWrapper<>();
        response.code = -1;
        response.message = message;
        return response;
    }
}