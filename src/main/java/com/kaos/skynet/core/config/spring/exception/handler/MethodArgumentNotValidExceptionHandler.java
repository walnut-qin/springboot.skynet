package com.kaos.skynet.core.config.spring.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.kaos.skynet.core.config.spring.exception.util.ExceptionResponse;

import org.springframework.core.annotation.Order;
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
    public ExceptionResponse argumentExceptionHandler(MethodArgumentNotValidException ex) {
        // 获取错误内容
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        var errMsg = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));

        return new ExceptionResponse() {
            @Override
            public Integer getCode() {
                return -1;
            }

            @Override
            public String getMessage() {
                return errMsg;
            }
        };
    }
}
