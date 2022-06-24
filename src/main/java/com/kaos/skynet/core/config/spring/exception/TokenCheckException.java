package com.kaos.skynet.core.config.spring.exception;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kaos.skynet.core.util.json.GsonWrapper;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.Getter;

/**
 * 自定义异常：Token校验异常
 */
public class TokenCheckException extends Exception {
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

    @Order(0)
    @ControllerAdvice
    static class ConversionNotSupportedExceptionHandler {
        /**
         * 序列化工具
         */
        static GsonWrapper gsonWrapper = new GsonWrapper();

        /**
         * 处理转换异常
         * 
         * @param ex
         * @return
         */
        @ExceptionHandler(value = TokenCheckException.class)
        public ResponseEntity<Map<String, Object>> exceptionHandler(TokenCheckException ex) {
            // 构造Header
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json;charset=UTF-8");

            // 构造body
            Map<String, Object> body = Maps.newHashMap();
            body.put("code", -1);
            body.put("message", ex.getMessage());

            // 构造响应体
            return new ResponseEntity<>(body, headers, HttpStatus.OK);
        }
    }
}
