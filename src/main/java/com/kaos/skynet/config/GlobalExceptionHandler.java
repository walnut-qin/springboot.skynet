package com.kaos.skynet.config;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 日志对象
    static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> exceptionHandler(HttpServletRequest req, Exception e) {
        // 获取错误信息
        String errMsg = e.getCause().getMessage();

        // 构造响应
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        return new ResponseEntity<>(errMsg, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> argumentExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException ex) {
        // 获取错误内容
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        var errMsg = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));

        // 构造响应
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        return new ResponseEntity<>(errMsg, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
