package com.kaos.skynet.config.exception;

import com.kaos.skynet.core.http.RspWrapper;
import com.kaos.skynet.core.json.Json;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConversionNotSupportedExceptionHandler {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 处理转换异常
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ConversionNotSupportedException.class)
    public ResponseEntity<RspWrapper<Object>> exceptionHandler(ConversionNotSupportedException ex) {
        // 构造Header
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json;charset=UTF-8");

        // 构造错误信息
        String errInfo = String.format("类型转换%s->%s失败, 值: %s, 原因: %s",
                ex.getValue().getClass().getName(),
                ex.getRequiredType().getName(),
                json.toJson(ex.getValue()),
                ex.getMessage());

        // 构造响应体
        return new ResponseEntity<>(RspWrapper.wrapFailResponse(errInfo), headers, HttpStatus.OK);
    }
}
