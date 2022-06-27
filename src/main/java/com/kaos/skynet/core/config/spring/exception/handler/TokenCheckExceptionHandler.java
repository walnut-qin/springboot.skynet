package com.kaos.skynet.core.config.spring.exception.handler;

import com.kaos.skynet.core.config.spring.exception.TokenCheckException;
import com.kaos.skynet.core.config.spring.exception.util.ExceptionResponse;
import com.kaos.skynet.core.util.json.GsonWrapper;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(1)
@ControllerAdvice
class TokenCheckExceptionHandler {
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
    public ExceptionResponse exceptionHandler(TokenCheckException ex) {
        return new ExceptionResponse() {
            @Override
            public Integer getCode() {
                return ex.getCode();
            }

            @Override
            public String getMessage() {
                return ex.getMessage();
            }
        };
    }
}
