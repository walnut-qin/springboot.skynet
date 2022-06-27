package com.kaos.skynet.core.config.spring.exception.handler;

import com.kaos.skynet.core.config.spring.exception.util.ExceptionResponse;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Order(Integer.MAX_VALUE)
@ControllerAdvice
class OtherExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ExceptionResponse exceptionHandler(Exception ex) {
        return new ExceptionResponse() {
            @Override
            public Integer getCode() {
                return -1;
            }

            @Override
            public String getMessage() {
                return ex.getMessage();
            }
        };
    }
}
