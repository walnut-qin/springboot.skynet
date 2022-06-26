package com.kaos.skynet.core.config.spring.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j;

@Log4j
@Order(Integer.MAX_VALUE - 1)
@ControllerAdvice
class ClientAbortExceptionHandler {
    @ExceptionHandler(value = ClientAbortException.class)
    @ResponseBody
    public void clientAbortExceptionHandler(HttpServletRequest req, ClientAbortExceptionHandler ex) {
        // 获取错误内容
        log.warn("客户端主动断链");
    }
}
