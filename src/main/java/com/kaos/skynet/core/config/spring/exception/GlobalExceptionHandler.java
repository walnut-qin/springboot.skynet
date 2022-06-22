package com.kaos.skynet.core.config.spring.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.ClientAbortException;
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

import lombok.extern.log4j.Log4j;

@Log4j
@ControllerAdvice
class GlobalExceptionHandler {
    // 日志对象
    static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> exceptionHandler(HttpServletRequest req, Exception e) {
        // 获取错误信息
        String errMsg = null;
        if (e.getCause() == null) {
            errMsg = e.getMessage();
            log.warn("cause为空, message = ".concat(errMsg));
        } else {
            errMsg = e.getCause().getMessage();
        }

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

    @ExceptionHandler(value = ClientAbortException.class)
    @ResponseBody
    public ResponseEntity<String> clientAbortExceptionHandler(HttpServletRequest req, ClientAbortException ex) {
        // 获取错误内容
        log.warn("客户端主动断链");

        // 构造响应
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        return new ResponseEntity<>("客户端断开链接", headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
