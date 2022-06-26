package com.kaos.skynet.core.config.spring.exception.handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Order(Integer.MAX_VALUE - 2)
@ControllerAdvice
class MethodArgumentNotValidExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> argumentExceptionHandler(MethodArgumentNotValidException ex) {
        // 获取错误内容
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        var errMsg = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));

        // 构造header
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json;charset=UTF-8");

        // 构造body
        Map<String, Object> body = Maps.newHashMap();
        body.put("code", -1);
        body.put("message", errMsg);

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
