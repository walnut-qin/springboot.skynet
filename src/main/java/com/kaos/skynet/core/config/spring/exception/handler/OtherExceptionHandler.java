package com.kaos.skynet.core.config.spring.exception.handler;

import java.util.Map;

import com.google.common.collect.Maps;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Order(Integer.MAX_VALUE)
@ControllerAdvice
class OtherExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> exceptionHandler(Exception ex) {
        // 构造响应
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json;charset=UTF-8");

        // 构造body
        Map<String, Object> body = Maps.newHashMap();
        body.put("code", -1);
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
